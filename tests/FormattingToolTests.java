package ToolRentalAgreement.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.Test;

import ToolRentalAgreement.src.FormattingTools;

public class FormattingToolTests {

	@Test
	public void convertStringToLocalDate_Valid_Format() {
		final LocalDate date = LocalDate.of(2018, 8, 28);
		final LocalDate date1 = FormattingTools.convertStringToLocalDate("8/28/18", "M/dd/yy");
		assertEquals(date, date1);
	}

	@Test(expected = DateTimeParseException.class)
	public void convertStringToLocalDate_Invalid_Format() {
		final LocalDate date = LocalDate.of(2018, 8, 28);
		final LocalDate date1 = FormattingTools.convertStringToLocalDate("8/28/18", "");
		assertEquals(date, date1);
	}

	@Test
	public void percentageOf() {
		assertEquals(new BigDecimal(5).setScale(2, RoundingMode.HALF_UP), FormattingTools.percentageOf(new BigDecimal(10), 50));
	}

	@Test
	public void roundToCurrency() {
		assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), FormattingTools.roundToCurrency(new BigDecimal(1.504)));
	}

	@Test
	public void bigDecimalToCurrency() {
		assertEquals("$1.00", FormattingTools.bigDecimalToCurrency(new BigDecimal(1)));
	}

}
