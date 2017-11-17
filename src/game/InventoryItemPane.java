package game;

import java.awt.*;
import java.util.ArrayList;

public class InventoryItemPane extends MutableTextPane {

	private static int INDICATOR_ALIGNMENT = 5;
	private static int BACKPACK_ALIGNMENT = 9;

	private static String LEFT_INDICATOR = ">>  ";

	private ArrayList<String> backpack = new ArrayList<>();
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

	private void addItem(String backpackItem, boolean selected) {
		if (selected) {
			for (int i = 0; i < INDICATOR_ALIGNMENT; i++) {
				appendText(" ", Color.white);
			}
			// add selection marker
			appendText(LEFT_INDICATOR, Color.green);
		} else {
			for (int i = 0; i < BACKPACK_ALIGNMENT; i++) {
				appendText(" ", Color.white);
			}
		}
		// Display name of backpack item at this position
		char[] bpChars = backpackItem.toCharArray();
		for (char c : bpChars) {
			appendText(Character.toString(c), colorForChar(c));
		}
		appendText("\n", Color.white);
	}

	private void buildInventoryList() {
		for (int i = 0; i < backpack.size(); i++) {
			boolean isSelected = false;
			if (i == curr) {
				isSelected = true;
			}
			addItem(backpack.get(i), isSelected);
		}
	}

	public void setBackpack(ArrayList<String> backpack) {
		this.backpack = backpack;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}
}
