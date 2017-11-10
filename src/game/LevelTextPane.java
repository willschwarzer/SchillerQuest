package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class LevelTextPane extends JTextPane {
	private char[][] characterGrid;

	private void drawCharGridInPane() {
		setEditable(true);
		setText("");

		for (char[] row : characterGrid) {
			for (char c : row) {
				appendText(Character.toString(c), colorForChar(c));
			}
			appendText("\n", Color.white);
		}
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
			case '@':
				return Color.green;
			case 'X':
				return Color.red;
			case '*':
				return Color.cyan;
			case '$':
				return Color.yellow;
			case '%':
				return Color.magenta;
			case '#':
				return Color.gray;
			default:
				return Color.white;
		}
	}

	public void updateCharacterGrid(char[][] newGrid) {
		characterGrid = newGrid;
		drawCharGridInPane();
	}
}
