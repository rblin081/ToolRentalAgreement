package Cardinal.src;

public class ToolFactory {

	public static Tool getTool(final String sCode) {
		// Needs min 2 characters, 1 for Tool 1 for brand
		if (sCode == null || sCode.equals(Constants.EMPTY) || sCode.length() < 2) {
			throw new IllegalArgumentException("Tool Code is not defined.");
		}

		final int iCodeLength = sCode.length();
		final String sToolType = sCode.substring(0, iCodeLength - 1);
		final String sToolBrand = sCode.substring(iCodeLength - 1, iCodeLength);

		final Tool.Type toolType = Tool.Type.fromString(sToolType);
		final Tool.Brand toolBrand = Tool.Brand.fromString(sToolBrand);

		// could also add checks for is type / brand none
		if (!toolType.hasBrand(toolBrand)) {
			throw new IllegalArgumentException("Tool is not carried in that brand.");
		}
		return new Tool(toolType, toolBrand);
	}

}
