package ToolRentalAgreement.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ToolRentalAgreement.src.ToolFactory;

public class ToolFactoryTests {

	@Test(expected = IllegalArgumentException.class)
	public void ToolFactory_InvalidCode() {
		ToolFactory.getTool("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void ToolFactory_InvalidBrandType() {
		ToolFactory.getTool("LADE");
	}

	@Test
	public void ToolFactory_ValidCode_Ladder() {
		final Tool ladder = ToolFactory.getTool("LADW");
		assertEquals(Tool.Type.LADDER, ladder.getType());
		assertEquals(Tool.Brand.WERNER, ladder.getBrand());
		assertEquals("LADW", ladder.getCode());
	}
}
