package game;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventoryCharacterPane extends MutableTextPane {
	Map<String, InventoryItem> equippedItems = new HashMap<>();
	String[] itemTypes = new String[5];
	String selectedType;

	private static String characterString =
			// @formatter:off
			"        ___  \n" +
			"  |. .| \n" +
			"  | - | \n" +
			"\\__( )__/\n" +
			"  ||~||      \n" +
			"       \\___/      \n" +
			"       /   \\      \n" +
			"     _|     |_    ";
			// @formatter:on

	private static String[] characterLines = characterString.split(System.getProperty("line.separator"));

	private static String sword =
			// @formatter:off
			" | \n" +
			" | \n" +
			"\\_/\n" +
			"| ";
			// @formatter:on

	private static String[] swordLines = sword.split(System.getProperty("line.separator"));

	private static String shield =
			// @formatter:off
			" ___ \n" +
			"|***|\n" +
			"\\***/\n" +
			" \\_/ ";
			// @formatter:on

	private static String[] shieldLines = shield.split(System.getProperty("line.separator"));

	public InventoryCharacterPane() {
		super(18);
		itemTypes[0] = "weapon";
		itemTypes[1] = "shield";
		itemTypes[2] = "amulet";
		itemTypes[3] = "armor";
		itemTypes[4] = "shoes";

		drawPane();
	}

	@Override
	public void composeLinesOfText() {
		String lineBreaks = "\n\n";
		addLine(lineBreaks);
		drawCharacter();
		drawItemList();
	}

	private void drawItemList() {
		String header = "\n Equipped:        \n" + " =========        ";
		addLine(header);

		for (String t : itemTypes) {
			displayItemOfType(t);
		}
	}

	private void drawCharacter() {
		for (int i = 0; i < characterLines.length; i++) {
			appendStringWithColor(" ", Color.white); // add a left margin
			// Shield comes before character
			if (i >= 1 && i <= 4) {
				// Draw shield on these lines
				if (Objects.equals(selectedType, "shield")) {
					appendStringWithColor(shieldLines[i - 1], Color.green);
				} else {
					appendStringWithColor(shieldLines[i - 1], Color.white);
				}
			}

			// Amulet and armor are an entire line of character
			if ((i == 4 && Objects.equals(selectedType, "amulet")) || (i == 5 && Objects.equals(selectedType, "armor"))) {
				// Draw amulet and armor on these lines
				appendStringWithColor(characterLines[i], Color.green);
			} else if (i == 7 && Objects.equals(selectedType, "shoes")) {
				// Draw shoes on this line
				appendStringWithColor(characterLines[i], Color.green);
			} else {
				// Just draw the line of the character
				appendStringWithColor(characterLines[i], Color.white); // sword comes after the character
			}

			// Draw sword on these lines
			if (i >= 0 && i <= 3) {
				if (Objects.equals(selectedType, "weapon")) {
					appendStringWithColor(swordLines[i], Color.green);
				} else {
					appendStringWithColor(swordLines[i], Color.white);
				}
			}
			appendStringWithColor(System.lineSeparator(), Color.white);
		}
	}

	private void displayItemOfType(String type) {
		if (equippedItems.containsKey(type)) {
			addLine(" " + symbolForType(type) + " " + equippedItems.get(type).getName() + selectionIndicator(type));
		} else {
			addLine(" " + symbolForType(type) + " (empty)" + selectionIndicator(type));
		}
	}

	private String selectionIndicator(String type) {
		if (Objects.equals(type, selectedType)) {
			return " <<";
		} else {
			return "";
		}
	}

	public void setEquippedItems(Map<String, InventoryItem> items) {
		equippedItems = items;
	}

	public void setSelectedType(String type) {
		this.selectedType = type;
	}
}
