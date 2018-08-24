package Cardinal.src;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormattingTools {

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	private static final int DEFAULT_DECIMAL_SCALE = 2;
	private static final NumberFormat BIG_DECIMAL_CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();

	/**
	 *
	 * @param sDate
	 *            String representation of date
	 * @param sFormat
	 *            Format sDate should follow
	 * @return LocalDate set to sDate
	 */
	public static LocalDate convertStringToLocalDate(final String sDate, final String sFormat) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(sFormat);
		return LocalDate.parse(sDate, formatter);
	}

	/**
	 *
	 * @param base
	 *            Whole amount
	 * @param pct
	 *            Percentage of whole amount
	 * @return Value of percentage of the whole, formatted to two decimal places
	 */
	public static BigDecimal percentageOf(final BigDecimal base, final int pct) {
		return percentageOf(base, pct, DEFAULT_DECIMAL_SCALE);
	}

	/**
	 *
	 * @param base
	 *            Whole amount
	 * @param pct
	 *            Percentage of whole amount
	 * @param scale
	 *            Number of decimal places BigDecimal should be rounded to
	 * @return Value of percentage of the whole
	 */
	public static BigDecimal percentageOf(final BigDecimal base, final int pct, final int scale) {
		return base.multiply(new BigDecimal(pct)).divide(ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 *
	 * @param d
	 *            BigDecimal to be rounded to two decimal places
	 * @return BigDecimal rounded to two decimal places
	 */
	public static BigDecimal roundToCurrency(final BigDecimal d) {
		return d.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 *
	 * @param d
	 *            BigDecimal amount to be formatted into currency
	 * @return String representing BigDecimal as two decimal places eg $2.00
	 */
	public static String bigDecimalToCurrency(final BigDecimal d) {
		return BIG_DECIMAL_CURRENCY_FORMATTER.format(d);
	}
}
