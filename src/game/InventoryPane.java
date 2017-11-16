package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPane extends JTextPane {
	private static int BACKPACK_ALIGNMENT = 16;
	private static int EQUIPPED_ALIGNMENT = 38;

	private static String LEFT_INDICATOR = " =>";
	private static String RIGHT_INDICATOR = "<= ";

	private ArrayList<String> backpack = new ArrayList<>();
	private ArrayList<String> equipped = new ArrayList<>();
	private int curr = 0;

	public InventoryPane() {
    	super();

		//**
		//TODO: all of this should happen in model
		backpack.add("Cool Sword");
		backpack.add("Rusty Spoon");
		backpack.add("Freedom Key");
		equipped.add("Cooler Sword");
		equipped.add("Modest Loins");
		equipped.add("Justice Amulet");
		//**

		drawPane();
	}

	private void drawPane() {
		setEditable(true);
		setText("");
		addHeaderText();
		buildInventoryList();
		setEditable(false);
		setBackground(Color.black);
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		setEditable(false);
	}

	private void addHeaderText() {
		String header =
				"                            _      ___  _   _                  \n" +
				"               | |\\ | \\  / |_ |\\ |  |  | | |_| \\ /             \n" +
				"               | | \\|  \\/  |_ | \\|  |  |_| | \\  |              \n" +
				"                                                               ";
		addLine(header);
	}

	private void addItemPair(String backpackItem, String equippedItem, boolean selected) {
		char[] bpChars = backpackItem.toCharArray();
		char[] eqChars = equippedItem.toCharArray();
		// Add a left margin of spaces
		int n = 0;
		while (n < BACKPACK_ALIGNMENT) {
			appendText(" ", Color.white);
			n++;
		}
		// Display name of backpack item at this position
		int i = 0;
		while (i < bpChars.length) {
			appendText(Character.toString(bpChars[i]), colorForChar(bpChars[i]));
			i++;
		}
		int index = i + n;
		// Add selection indicators if applicable
		if (selected) {
			if (curr < backpack.size()) {
				appendText(LEFT_INDICATOR, Color.green);
				index += LEFT_INDICATOR.length();
			}

			if (curr < equipped.size()) {
				while (index < EQUIPPED_ALIGNMENT - 3) {
					appendText(" ", Color.white);
					index++;
				}
				appendText(RIGHT_INDICATOR, Color.green);
			}
		} else {
			// Move cursor to position for equipped item
			while (index < EQUIPPED_ALIGNMENT) {
				appendText(" ", Color.white);
				index++;
			}
		}
		// Display name of equipped item at this position
		for (char c : eqChars) {
			appendText(Character.toString(c), colorForChar(c));
		}
		appendText("\n", Color.white);
	}

	private void addLine(String line) {
		for (char c : line.toCharArray()) {
			appendText(Character.toString(c), colorForChar(c));
		}
		appendText("\n", Color.white);
	}

	private void buildInventoryList() {
		addLine("                 Backpack:             Equipped:                  ");
		addLine("                 =========             =========                  ");
		int i = 0;
		while (i < backpack.size() || i < equipped.size()) {
			boolean isSelected = false;
			if (i == curr) {
				isSelected = true;
			}

			if (i < backpack.size() && i < equipped.size()) {
				addItemPair(backpack.get(i), equipped.get(i), isSelected);
			} else if (i < backpack.size() && i >= equipped.size()) {
				addItemPair(backpack.get(i), "", isSelected);
			} else if (i >= backpack.size() && i < equipped.size()) {
				addItemPair("", equipped.get(i), isSelected);
			}
			i++;
		}
	}

	private void appendText(String text, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet attrSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		int len = getDocument().getLength();
		setCaretPosition(len);
		setCharacterAttributes(attrSet, false);
		replaceSelection(text);
	}

	private Color colorForChar(char c) {
		switch (c) {
			case '>':
				return Color.green;
			case '<':
				return Color.green;
			case '=':
				return Color.green;
			case '#':
				return Color.gray;
			default:
				return Color.white;
		}
	}

	public void selectUp() {
		if (curr > 0) {
			curr--;
		}
		drawPane();
	}

	public void selectDown() {
		if (curr < backpack.size() - 1 || curr < equipped.size() - 1) {
			curr++;
		}
		drawPane();
	}

	//TODO: move this to model?
	// Moves item to backpack section
	public void moveLeft() {
		if (equipped.size() >= curr) { // valid change?
			backpack.add(equipped.get(curr));
			equipped.remove(curr);
			if (curr >= backpack.size() && curr >= equipped.size())
				curr = equipped.size() - 1;
			if (curr < 0)
				curr = 0;
		}
		drawPane();
	}

	// Moves item to equipped section
	public void moveRight() {
		if (backpack.size() >= curr) { // valid change?
			equipped.add(backpack.get(curr));
			backpack.remove(curr);
			if (curr >= backpack.size() && curr >= equipped.size())
				curr = backpack.size() - 1;
			if (curr < 0)
				curr = 0;
		}
		drawPane();
	}
}
