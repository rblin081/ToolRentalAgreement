package ToolRentalAgreement.src;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

public class ToolRentalAgreement {

	private static final String RENTAL_DATE_FORMAT = "M/d/yy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(RENTAL_DATE_FORMAT);

	public static final Set<DayOfWeek> WEEKDAYS = new HashSet<DayOfWeek>();
	public static final Set<DayOfWeek> WEEKEND_DAYS = new HashSet<DayOfWeek>();

	public static final int DISCOUNT_MINIMUM_PERCENTAGE = 0;
	public static final int DISCOUNT_MAXIMUM_PERCENTAGE = 100;

	public static final int MINIMUM_RENTAL_DAYS = 1;

	static {
		WEEKDAYS.add(DayOfWeek.MONDAY);
		WEEKDAYS.add(DayOfWeek.TUESDAY);
		WEEKDAYS.add(DayOfWeek.WEDNESDAY);
		WEEKDAYS.add(DayOfWeek.THURSDAY);
		WEEKDAYS.add(DayOfWeek.FRIDAY);

		WEEKEND_DAYS.add(DayOfWeek.SATURDAY);
		WEEKEND_DAYS.add(DayOfWeek.SUNDAY);

	}

	/**
	 * Returns a RentalAgreement that contains all information for the given rental
	 *
	 * @param sToolCode
	 *            Code representing a unique tool and brand combination
	 * @param sCheckoutDate
	 *            String representation of date for beginning of tool rental. Follows the format M/dd/yy
	 * @param iRentalDays
	 *            Specifies number of days tool will be rented
	 * @param iDiscountPercentage
	 *            Discount percentage to be applied to calculated charge amount
	 */
	public static ToolRentalAgreement generateRentalAgreement(final String sToolCode, final String sCheckoutDate, final int iRentalDays,
			final int iDiscountPercentage) {

		if (iRentalDays < MINIMUM_RENTAL_DAYS) {
			throw new IllegalArgumentException("Rental Period has to be at least one day.");
		}

		if (iDiscountPercentage < DISCOUNT_MINIMUM_PERCENTAGE || iDiscountPercentage > DISCOUNT_MAXIMUM_PERCENTAGE) {
			throw new IllegalArgumentException("Rental discounts must be between 0-100%.");
		}

		// As stated in the exercise instructions, the Tool Code is unique to a Tool / Brand combination
		// With that said I felt it best to use a factory for Tool instantiation
		final Tool tool = ToolFactory.getTool(sToolCode);

		if (tool.getType() == Tool.Type.NONE || tool.getBrand() == Tool.Brand.NONE) {
			throw new IllegalArgumentException("Unable to find Tool / Brand corresponding to Tool Code:" + sToolCode + ".");
		}

		final ToolRentalInformation toolRentalInformation = ToolDataStore.getInstance().getToolRentalInformation(tool.getType());
		final LocalDate checkoutDate = FormattingTools.convertStringToLocalDate(sCheckoutDate, RENTAL_DATE_FORMAT);
		int iChargeDays = 0;
		// Start at 1 instead of 0, using to increment
		for (int i = 1; i < iRentalDays + 1; i++) {
			final LocalDate date = checkoutDate.plusDays(i);
			final DayOfWeek day = date.getDayOfWeek();
			// if day is holiday it overrides other weekday / weekend rules
			if (isHoliday(date)) {
				if (toolRentalInformation.hasHolidayCharge()) {
					iChargeDays++;
				}
				continue;
			} else if ((toolRentalInformation.hasWeekdayCharge() && WEEKDAYS.contains(day))) {
				iChargeDays++;
			} else if ((toolRentalInformation.hasWeekendCharge() && WEEKEND_DAYS.contains(day))) {
				iChargeDays++;
			}
		}

		final ToolRentalAgreement rentalAgreement = new ToolRentalAgreement(iChargeDays, iDiscountPercentage, toolRentalInformation.getDailyCharge());
		// Date setter(s)
		rentalAgreement.setTransactionDateInfo(checkoutDate, iRentalDays);
		// Tool Info setter(s)
		rentalAgreement.setToolInfo(sToolCode, tool.getType(), tool.getBrand());
		// Calculate checkout information
		rentalAgreement.CalculateRentalAmts();
		return rentalAgreement;
	}

	private static boolean isHoliday(final LocalDate date) {
		boolean isIndependenceHoliday = false;
		if (date.getMonth().equals(Month.JULY) && WEEKDAYS.contains(date.getDayOfWeek())) {
			final LocalDate independenceDay = LocalDate.of(date.getYear(), Month.JULY.getValue(), 4);
			isIndependenceHoliday = independenceDay.plusDays(1).equals(date) || independenceDay.minusDays(1).equals(date);
		}
		final boolean isLaborDay = date.getMonth().equals(Month.SEPTEMBER)
				&& date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).equals(date);
		return isIndependenceHoliday || isLaborDay;
	}

	// Some fields aren't used, but specified as needed in the final Rental Agreement
	private LocalDate dueDate;
	private LocalDate checkoutDate;
	private int iRentalDays;
	private final int iChargeDays;
	private final int iDiscountPct;
	private String sToolCode;
	private Tool.Type toolType;
	private Tool.Brand toolBrand;
	private BigDecimal dPreDiscountAmt;
	private final BigDecimal dDailyRentalCharge;
	private BigDecimal dDiscountAmt;
	private BigDecimal dFinalCharge;

	// Mandatory fields for performing calculations
	private ToolRentalAgreement(final int iChargeDays, final int iDiscountPct, final double dDailyRentalCharge) {
		this.iChargeDays = iChargeDays;
		this.iDiscountPct = iDiscountPct;
		this.dDailyRentalCharge = FormattingTools.roundToCurrency(new BigDecimal(dDailyRentalCharge));
	}

	private void setTransactionDateInfo(final LocalDate checkoutDate, final int iRentalDays) {
		this.checkoutDate = checkoutDate;
		this.iRentalDays = iRentalDays;
		this.dueDate = checkoutDate.plusDays(iRentalDays);
	}

	private void setToolInfo(final String sToolCode, final Tool.Type type, final Tool.Brand brand) {
		this.sToolCode = sToolCode;
		this.toolType = type;
		this.toolBrand = brand;
	}

	private void CalculateRentalAmts() {
		this.dPreDiscountAmt = FormattingTools.roundToCurrency(this.dDailyRentalCharge.multiply(new BigDecimal(this.iChargeDays)));
		this.dDiscountAmt = FormattingTools.percentageOf(dPreDiscountAmt, this.iDiscountPct);
		this.dFinalCharge = FormattingTools.roundToCurrency(this.dPreDiscountAmt.subtract(this.dDiscountAmt));
	}

	public String getToolCode() {
		return this.sToolCode;
	}

	public Tool.Type getToolType() {
		return this.toolType;
	}

	public Tool.Brand getToolBrand() {
		return this.toolBrand;
	}

	public String getFormattedCheckoutDate() {
		return this.checkoutDate.format(DATE_FORMATTER);
	}

	public int getChargeDays() {
		return this.iChargeDays;
	}

	public int getRentalDays() {
		return this.iRentalDays;
	}

	public String getFormattedPreDiscountAmt() {
		return FormattingTools.bigDecimalToCurrency(this.dPreDiscountAmt);
	}

	public String getFormattedDueDate() {
		return this.dueDate.format(DATE_FORMATTER);
	}

	public String getFormattedDailyChargeAmt() {
		return FormattingTools.bigDecimalToCurrency(this.dDailyRentalCharge);
	}

	public String getFormattedDiscountPct() {
		return String.valueOf(iDiscountPct) + "%";
	}

	public String getFormattedDiscountAmt() {
		return FormattingTools.bigDecimalToCurrency(this.dDiscountAmt);
	}

	public String getFormattedFinalChargeAmt() {
		return FormattingTools.bigDecimalToCurrency(this.dFinalCharge);
	}

}
