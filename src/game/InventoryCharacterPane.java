package game;

import java.util.HashMap;
import java.util.Map;

public class InventoryCharacterPane extends MutableTextPane {
	Map<String, String> equippedItems = new HashMap<>();
	String[] itemTypes = new String[5];
	String selectedType;


	public InventoryCharacterPane() {
		super(18);
		itemTypes[0] = "weapon";
		itemTypes[1] = "shield";
		itemTypes[2] = "amulet";
		itemTypes[3] = "armor";
		itemTypes[4] = "shoes";

		equippedItems.put("weapon", "Rusty Spoon");
		equippedItems.put("shield", "Legend Shield");
		equippedItems.put("amulet", "Cool Amulet");

		drawPane();
	}

	public void composeLinesOfText() {
		String lineBreaks = "\n \n";
		addLine(lineBreaks);
		String character =
				"        ___   |   \n" +
						" ___   |. .|  |   \n" +
						"|***|  | - | \\_/  \n" +
						"\\***/\\__( )__/|   \n" +
						" \\_/   ||~||      \n" +
						"       \\___/      \n" +
						"       /   \\      \n" +
						"     _|     |_    \n";
		addLine(character);

		String header =
				" Equipped:        \n" +
						" =========        ";
		addLine(header);

		for (String t : itemTypes) {
			displayItemOfType(t);
		}
	}

	private void displayItemOfType(String type) {
		if (equippedItems.containsKey(type)) {
			addLine(" " + symbolForType(type) + " " + equippedItems.get(type));
		} else {
			addLine(" " + symbolForType(type) + " (empty)");
		}
	}

	private String symbolForType(String type) {
		switch (type) {
			case "weapon":
				return "△";
			case "shield":
				return "▯";
			case "amulet":
				return "◎";
			case "armor":
				return "೧";
			case "shoes":
				return "◣";
			default:
				return "*";
		}
	}
}