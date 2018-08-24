package ToolRentalAgreement.src;

public class ToolRentalInformation {

	public static final boolean HAS_WEEKDAY_CHARGE = true;
	public static final boolean HAS_WEEKEND_CHARGE = true;
	public static final boolean HAS_HOLIDAY_CHARGE = true;

	public static final boolean NO_WEEKDAY_CHARGE = false;
	public static final boolean NO_WEEKEND_CHARGE = false;
	public static final boolean NO_HOLIDAY_CHARGE = false;

	// toolType currently not used, should keep tight coupling between the type and rental information
	private final Tool.Type toolType;
	private double dDailyCharge = 0;
	private boolean hasWeekdayCharge = false;
	private boolean hasWeekendCharge = false;
	private boolean hasHolidayCharge = false;

	/**
	 * Object representing specific rental information regarding a tool type
	 *
	 * @param toolType
	 *            Type of the tool
	 * @param dDailyCharge
	 *            Daily reoccuring charge for tool rental
	 * @param hasWeekdayCharge
	 *            Is eligible for weekday charges
	 * @param hasWeekendCharge
	 *            Is eligible for weekend charges
	 * @param hasHolidayCharge
	 *            is eligible for holiday charges
	 */
	public ToolRentalInformation(final Tool.Type toolType, final double dDailyCharge, final boolean hasWeekdayCharge, final boolean hasWeekendCharge,
			final boolean hasHolidayCharge) {
		this.toolType = toolType;
		this.dDailyCharge = dDailyCharge;
		this.hasWeekdayCharge = hasWeekdayCharge;
		this.hasWeekendCharge = hasWeekendCharge;
		this.hasHolidayCharge = hasHolidayCharge;
	}

	public double getDailyCharge() {
		return this.dDailyCharge;
	}

	public boolean hasWeekdayCharge() {
		return this.hasWeekdayCharge;
	}

	public boolean hasWeekendCharge() {
		return this.hasWeekendCharge;
	}

	public boolean hasHolidayCharge() {
		return this.hasHolidayCharge;
	}

}
