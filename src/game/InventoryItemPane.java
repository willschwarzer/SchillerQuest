package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class InventoryItemPane extends MutableTextPane {
	private static int INDICATOR_ALIGNMENT = 5;
	private static int BACKPACK_ALIGNMENT = 9;

	private static String LEFT_INDICATOR = ">>  ";

	private ArrayList<String> backpackItems = new ArrayList<>();
	private ArrayList<String> backpackItemTypes = new ArrayList<>();
	private int curr = 0;

	public InventoryItemPane() {
		super(18);
		drawPane();
	}

	public void composeLinesOfText() {
		addHeaderText();
		buildInventoryList();
	}

	private void addHeaderText() {
		String header =
				"                      _      ___  _   _           \n" +
				"         | |\\ | \\  / |_ |\\ |  |  | | |_| \\ /      \n" +
				"         | | \\|  \\/  |_ | \\|  |  |_| | \\  |       ";
		addLine(header);
		addLine("\n");
		addLine("         Backpack:");
		addLine("         =========");
	}

	private void addItem(String backpackItem, String itemType, boolean selected) {
		if (selected) {
			for (int i = 0; i < INDICATOR_ALIGNMENT; i++) {
				appendStringWithColor(" ", Color.white);
			}
			// add selection marker
			appendStringWithColor(LEFT_INDICATOR, Color.green);
		} else {
			for (int i = 0; i < BACKPACK_ALIGNMENT; i++) {
				appendStringWithColor(" ", Color.white);
			}
		}
		// Display corresponding symbol for this item type
		appendStringWithColor(Character.toString(symbolForType(itemType)), colorForChar(symbolForType(itemType)));
		appendStringWithColor(" ", Color.white);

		// Display name of backpack item at this position
		char[] bpChars = backpackItem.toCharArray();
		for (char c : bpChars) {
			appendStringWithColor(Character.toString(c), colorForChar(c));
		}
		appendStringWithColor("\n", Color.white);
	}

	private void buildInventoryList() {
		for (int i = 0; i < backpackItems.size(); i++) {
			boolean isSelected = false;
			if (i == curr) {
				isSelected = true;
			}
			addItem(backpackItems.get(i), backpackItemTypes.get(i), isSelected);
		}
	}

	public void setBackpack(ArrayList<String> items, ArrayList<String> itemTypes) {
		this.backpackItems = items;
		this.backpackItemTypes = itemTypes;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}

	public String getSelectedType() {
		return backpackItemTypes.get(curr);
	}
}