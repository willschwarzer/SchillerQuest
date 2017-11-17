package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class MutableTextPane extends JTextPane {
	private int fontSize;

	public MutableTextPane(int fontSize) {
		super();
		this.fontSize = fontSize;
	}

	public void drawPane() {
		setEditable(true);
		setText("");
		composeLinesOfText();
		setBackground(Color.black);
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
		setEditable(false);
	}

	public void composeLinesOfText() {
		// exists to be overridden
		System.out.println("uh oh");
	}

	public void addLine(String line) {
		for (char c : line.toCharArray()) {
			appendStringWithColor(Character.toString(c), colorForChar(c));
		}
		appendStringWithColor("\n", Color.white);
	}

	public void appendStringWithColor(String text, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet attrSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		int len = getDocument().getLength();
		setCaretPosition(len);
		setCharacterAttributes(attrSet, false);
		replaceSelection(text);
	}

	public Color colorForChar(char c) {
		switch (c) {
			case '>':
				return Color.green;
			case '<':
				return Color.green;
			case '=':
				return Color.green;
			case '#':
				return Color.gray;

			case '△':
				return Color.yellow;
			case '▯':
				return Color.cyan;
			case '◎':
				return Color.magenta;
			case '೧':
				return Color.orange;
			case '◣':
				return Color.gray;
			default:
				return Color.white;
		}
	}

	public char symbolForType(String type) {
		switch (type) {
			case "weapon":
				return '△';
			case "shield":
				return '▯';
			case "amulet":
				return '◎';
			case "armor":
				return '೧';
			case "shoes":
				return '◣';
			default:
				return '*';
		}
	}
}
