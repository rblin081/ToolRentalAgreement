package ToolRentalAgreement.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ToolRentalAgreement.src.ToolRentalAgreement;

public class RentalAgreementTests {

	@Test(expected = IllegalArgumentException.class)
	public void RentalAgreement_JackHammer_InvalidDiscountPct() {
		ToolRentalAgreement.generateRentalAgreement("JAKR", "9/13/15", 5, 101);
	}

	@Test(expected = IllegalArgumentException.class)
	public void RentalAgreement_JackHammer_InvalidRentalDays() {
		ToolRentalAgreement.generateRentalAgreement("JAKR", "9/13/15", -1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void RentalAgreement_JackHammer_InvalidToolCode() {
		ToolRentalAgreement.generateRentalAgreement("JAKK", "9/13/15", 5, 101);
	}

	/*
	 *
	 * The document specifies the rental days as 3, but the due date as 7/4/20.
	 * This contradicts all other examples where the due date is the checkout date plus rental days ie Test 3 7/2/15 + 5 Days = 7/7/15
	 * Furthermore the example test results put the charge days at 1, when the rules show there should be 2 charge days.
	 * 7/2/20 is on a Thursday, 7/4/20 is on a Saturday.
	 * According to the rules, if Independence Day falls on a weekend it will be observed on the closest weekday (Friday 07/03/20)
	 * Ladders charges occur on weekdays and weekends, but not holidays.
	 * 07/03/20 is a celebrated holiday, there are no charges
	 * 07/04/20 is a weekend and a charge day
	 * 07/05/20 is also a weekend and a charge day. This is also the due date
	 * I believe there was a typo in the Rental Days column for Test 2, it should have been 2 rental days instead of 3
	 * This matches the business logic and other test results
	 * If this was not the case of a typo, I apologize. I assure you that I read through the document
	 * approximately 1 million times and this is the conclusion I came to
	 */
	@Test
	public void RentalAgreement_Ladder_Holiday_Discount() {
		final ToolRentalAgreement rentalAgreement = ToolRentalAgreement.generateRentalAgreement("LADW", "7/2/20", 2, 10);
		assertEquals("7/4/20", rentalAgreement.getFormattedDueDate());
		assertEquals("$1.99", rentalAgreement.getFormattedDailyChargeAmt());
		assertEquals(1, rentalAgreement.getChargeDays());
		assertEquals("$1.99", rentalAgreement.getFormattedPreDiscountAmt());
		assertEquals("10%", rentalAgreement.getFormattedDiscountPct());
		assertEquals("$0.20", rentalAgreement.getFormattedDiscountAmt());
		assertEquals("$1.79", rentalAgreement.getFormattedFinalChargeAmt());
	}

	@Test
	public void RentalAgreement_Chainsaw_IndependenceDay_Discount() {
		final ToolRentalAgreement rentalAgreement = ToolRentalAgreement.generateRentalAgreement("CHNS", "7/2/15", 5, 25);
		assertEquals("CHNS", rentalAgreement.getToolCode());
		assertEquals(Tool.Type.CHAINSAW, rentalAgreement.getToolType());
		assertEquals(Tool.Brand.STIHL, rentalAgreement.getToolBrand());
		assertEquals(5, rentalAgreement.getRentalDays());
		assertEquals("7/2/15", rentalAgreement.getFormattedCheckoutDate());
		assertEquals("7/7/15", rentalAgreement.getFormattedDueDate());
		assertEquals("$1.49", rentalAgreement.getFormattedDailyChargeAmt());
		assertEquals(3, rentalAgreement.getChargeDays());
		assertEquals("$4.47", rentalAgreement.getFormattedPreDiscountAmt());
		assertEquals("25%", rentalAgreement.getFormattedDiscountPct());
		assertEquals("$1.12", rentalAgreement.getFormattedDiscountAmt());
		assertEquals("$3.35", rentalAgreement.getFormattedFinalChargeAmt());
	}

	@Test
	public void RentalAgreement_Jackhammer_LaborDay() {
		final ToolRentalAgreement rentalAgreement = ToolRentalAgreement.generateRentalAgreement("JAKD", "9/3/15", 6, 0);
		assertEquals("JAKD", rentalAgreement.getToolCode());
		assertEquals(Tool.Type.JACKHAMMER, rentalAgreement.getToolType());
		assertEquals(Tool.Brand.DEWALT, rentalAgreement.getToolBrand());
		assertEquals(6, rentalAgreement.getRentalDays());
		assertEquals("9/3/15", rentalAgreement.getFormattedCheckoutDate());
		assertEquals("9/9/15", rentalAgreement.getFormattedDueDate());
		assertEquals("$2.99", rentalAgreement.getFormattedDailyChargeAmt());
		assertEquals(3, rentalAgreement.getChargeDays());
		assertEquals("$8.97", rentalAgreement.getFormattedPreDiscountAmt());
		assertEquals("0%", rentalAgreement.getFormattedDiscountPct());
		assertEquals("$0.00", rentalAgreement.getFormattedDiscountAmt());
		assertEquals("$8.97", rentalAgreement.getFormattedFinalChargeAmt());
	}

	@Test
	public void RentalAgreement_Jackhammer_IndependenceDay() {
		final ToolRentalAgreement rentalAgreement = ToolRentalAgreement.generateRentalAgreement("JAKR", "7/2/15", 9, 0);
		assertEquals("JAKR", rentalAgreement.getToolCode());
		assertEquals(Tool.Type.JACKHAMMER, rentalAgreement.getToolType());
		assertEquals(Tool.Brand.RIDGID, rentalAgreement.getToolBrand());
		assertEquals(9, rentalAgreement.getRentalDays());
		assertEquals("7/2/15", rentalAgreement.getFormattedCheckoutDate());
		assertEquals("7/11/15", rentalAgreement.getFormattedDueDate());
		assertEquals("$2.99", rentalAgreement.getFormattedDailyChargeAmt());
		assertEquals(5, rentalAgreement.getChargeDays());
		assertEquals("$14.95", rentalAgreement.getFormattedPreDiscountAmt());
		assertEquals("0%", rentalAgreement.getFormattedDiscountPct());
		assertEquals("$0.00", rentalAgreement.getFormattedDiscountAmt());
		assertEquals("$14.95", rentalAgreement.getFormattedFinalChargeAmt());
	}

	@Test
	public void RentalAgreement_Jackhammer_IndependenceDay_Discount() {
		final ToolRentalAgreement rentalAgreement = ToolRentalAgreement.generateRentalAgreement("JAKR", "7/2/20", 4, 50);
		assertEquals("JAKR", rentalAgreement.getToolCode());
		assertEquals(Tool.Type.JACKHAMMER, rentalAgreement.getToolType());
		assertEquals(Tool.Brand.RIDGID, rentalAgreement.getToolBrand());
		assertEquals(4, rentalAgreement.getRentalDays());
		assertEquals("7/2/20", rentalAgreement.getFormattedCheckoutDate());
		assertEquals("7/6/20", rentalAgreement.getFormattedDueDate());
		assertEquals("$2.99", rentalAgreement.getFormattedDailyChargeAmt());
		assertEquals(1, rentalAgreement.getChargeDays());
		assertEquals("$2.99", rentalAgreement.getFormattedPreDiscountAmt());
		assertEquals("50%", rentalAgreement.getFormattedDiscountPct());
		assertEquals("$1.50", rentalAgreement.getFormattedDiscountAmt());
		assertEquals("$1.49", rentalAgreement.getFormattedFinalChargeAmt());
	}
}
