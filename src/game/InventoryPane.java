package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InventoryPane extends JTextPane {

	public InventoryPane() {

		drawPane();

		setBackground(Color.black);
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		setEditable(false);
	}

	private void drawPane() {
		setEditable(true);
		setText("");

		// temp implementation of loading dummy inventory from file
		File levelFile = new File("src/resources/inventory.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(levelFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				for (char c : line.toCharArray()) {
					appendText(Character.toString(c), colorForChar(c));
				}
				appendText("\n", Color.white);
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		// end temp implementation

		setEditable(false);
		setBackground(Color.black);
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		setEditable(false);
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
