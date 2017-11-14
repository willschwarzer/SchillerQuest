package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPane extends JTextPane {
	private ArrayList<String> backpack = new ArrayList<>();
	private ArrayList<String> equipped = new ArrayList<>();

	public InventoryPane() {
    	super();

		//**
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

	private void addItemPair(String backpackItem, String equippedItem) {
		char[] bpChars = backpackItem.toCharArray();
		char[] eqChars = equippedItem.toCharArray();

		int n = 0;
		while (n < 16) {
			appendText(" ", Color.white);
			n++;
		}
		int i = 0;
		while (i < bpChars.length) {
			appendText(Character.toString(bpChars[i]), colorForChar(bpChars[i]));
			i++;
		}
		int index = i + n;
		while (index < 38) {
			appendText(" ", Color.white);
			index++;
		}
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
			if (i < backpack.size() && i < equipped.size()) {
				addItemPair(backpack.get(i), equipped.get(i));
			} else if (i < backpack.size() && i >= equipped.size()) {
				addItemPair(backpack.get(i), "");
			} else if (i >= backpack.size() && i < equipped.size()) {
				addItemPair("", equipped.get(i));
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
}
