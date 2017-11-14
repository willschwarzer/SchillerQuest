package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InventoryPane extends JTextPane implements KeyListener {
	private static int LEFT_KEY = 37;
	private static int UP_KEY = 38;
	private static int RIGHT_KEY = 39;
	private static int DOWN_KEY = 40;

	private static int BACKPACK_ALIGNMENT = 16;
	private static int EQUIPPED_ALIGNMENT = 38;

	private static String LEFT_INDICATOR = " =>";
	private static String RIGHT_INDICATOR = "<= ";

	private ArrayList<String> backpack = new ArrayList<>();
	private ArrayList<String> equipped = new ArrayList<>();
	private int currSelectionIndex = 0;

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

		//TODO: make a much better keylistener
		addKeyListener(this);
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

			//todo if backpack available, display left indicator
			//todo if equipped available, display right indicator

			appendText(LEFT_INDICATOR, Color.green);
			index += LEFT_INDICATOR.length();

			while (index < EQUIPPED_ALIGNMENT - 3) {
				appendText(" ", Color.white);
				index++;
			}
			appendText(RIGHT_INDICATOR, Color.green);
			index += RIGHT_INDICATOR.length();
		}
		// Move cursor to position for equipped item
		while (index < EQUIPPED_ALIGNMENT) {
			appendText(" ", Color.white);
			index++;
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
			if (i == currSelectionIndex) {
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

	private void selectUp() {
		if (currSelectionIndex > 0) {
			currSelectionIndex--;
		}
	}

	private void selectDown() {
		if (currSelectionIndex < backpack.size() - 1 || currSelectionIndex < equipped.size() - 1) {
			currSelectionIndex++;
		}
	}

	private void moveToBackpack() {
		if (equipped.size() >= currSelectionIndex) { // valid change?
			backpack.add(equipped.get(currSelectionIndex));
			equipped.remove(currSelectionIndex);
			if (currSelectionIndex >= equipped.size()) {
				currSelectionIndex = equipped.size() - 1;
			}

			if (currSelectionIndex < 0)
				currSelectionIndex = 0;
		}
	}

	private void moveToEquipped() {
		if (backpack.size() >= currSelectionIndex) { // valid change?
			equipped.add(backpack.get(currSelectionIndex));
			backpack.remove(currSelectionIndex);
			if (currSelectionIndex >= backpack.size()) {
				currSelectionIndex = backpack.size() - 1;
			}

			if (currSelectionIndex < 0)
				currSelectionIndex = 0;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == UP_KEY) {
			selectUp();
		} else if (e.getKeyCode() == DOWN_KEY) {
			selectDown();
		} else if (e.getKeyCode() == LEFT_KEY) {
			moveToBackpack();
		} else if (e.getKeyCode() == RIGHT_KEY) {
			moveToEquipped();
		}
		drawPane();
    }

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
