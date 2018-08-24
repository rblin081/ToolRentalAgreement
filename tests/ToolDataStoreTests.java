package ToolRentalAgreement.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import ToolRentalAgreement.src.Tool;
import ToolRentalAgreement.src.ToolDataStore;
import ToolRentalAgreement.src.ToolRentalInformation;

public class ToolDataStoreTests {

	@Test
	public void Store_Is_Singleton() {
		final ToolDataStore d1 = ToolDataStore.getInstance();
		final ToolDataStore d2 = ToolDataStore.getInstance();
		assertEquals(d1, d2);
	}

	@Test
	public void Store_ValidEntry_Ladder() {
		final ToolDataStore d1 = ToolDataStore.getInstance();
		final ToolRentalInformation rentalInfo = d1.getToolRentalInformation(Tool.Type.LADDER);
		assertEquals(1.99, rentalInfo.getDailyCharge(), .1);
		assertEquals(true, rentalInfo.hasWeekdayCharge());
		assertEquals(true, rentalInfo.hasWeekendCharge());
		assertEquals(false, rentalInfo.hasHolidayCharge());
	}

	@Test
	public void Store_InvalidEntry_NoneType() {
		final ToolDataStore d1 = ToolDataStore.getInstance();
		final ToolRentalInformation rentalInfo = d1.getToolRentalInformation(Tool.Type.NONE);
		assertNull(rentalInfo);
	}

}
