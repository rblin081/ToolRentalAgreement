package Cardinal.src;

import java.util.HashMap;
import java.util.Map;

public class ToolDataStore {

	private static ToolDataStore instance = new ToolDataStore();

	// Singleton as there should only be one store
	public static ToolDataStore getInstance() {
		if (instance == null) {
			instance = new ToolDataStore();
		}
		return instance;
	}

	private final Map<Tool.Type, ToolRentalInformation> hmToolRentalInformation = new HashMap<Tool.Type, ToolRentalInformation>();

	/**
	 * Singleton object containing all rental information per tool
	 */
	private ToolDataStore() {
		// Ideally this would be read from a database or properties file. For simplicity the data will be hardcoded in this exercise
		final ToolRentalInformation ladderRentalInformation = new ToolRentalInformation(Tool.Type.LADDER, 1.99,
				ToolRentalInformation.HAS_WEEKDAY_CHARGE, ToolRentalInformation.HAS_WEEKEND_CHARGE, ToolRentalInformation.NO_HOLIDAY_CHARGE);
		final ToolRentalInformation chainsawRentalInformation = new ToolRentalInformation(Tool.Type.CHAINSAW, 1.49,
				ToolRentalInformation.HAS_WEEKDAY_CHARGE, ToolRentalInformation.NO_WEEKEND_CHARGE, ToolRentalInformation.HAS_HOLIDAY_CHARGE);
		final ToolRentalInformation jackhammerRentalInformation = new ToolRentalInformation(Tool.Type.JACKHAMMER, 2.99,
				ToolRentalInformation.HAS_WEEKDAY_CHARGE, ToolRentalInformation.NO_WEEKEND_CHARGE, ToolRentalInformation.NO_HOLIDAY_CHARGE);

		hmToolRentalInformation.put(Tool.Type.LADDER, ladderRentalInformation);
		hmToolRentalInformation.put(Tool.Type.CHAINSAW, chainsawRentalInformation);
		hmToolRentalInformation.put(Tool.Type.JACKHAMMER, jackhammerRentalInformation);

	}

	public ToolRentalInformation getToolRentalInformation(final Tool.Type type) {
		return hmToolRentalInformation.containsKey(type) ? hmToolRentalInformation.get(type) : null;
	}

}
