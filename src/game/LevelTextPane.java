package game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * LevelTextPane displays text in a window the ability to specify colors
 * for different characters. In the view hierarchy, it exists below GameFrame.
 */
public class LevelTextPane extends JTextPane {
	private char[][] characterGrid;
	private ControllerInterface controller;

	public LevelTextPane() {
		addBindings();
	}

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

	/**
	 * updateCharacterGrid is public so the controller
	 * can notify the LevelTextPane that a move has occurred
	 * and it's time to update the game window
	 */

	public void addBindings() {
		InputMap iMap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);

		iMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
		iMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
		iMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		iMap.put(KeyStroke.getKeyStroke("UP"), "up");

		ActionMap aMap = this.getActionMap();

		aMap.put("left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {-1, 0});
				System.out.println("Hello");
			}
		});
		aMap.put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {0, 1});
			}
		});
		aMap.put("right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {1, 0});
			}
		});
		aMap.put("up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {0, -1});
			}
		});
	}

	public void updateCharacterGrid(char[][] newGrid) {
		characterGrid = newGrid;
		drawCharGridInPane();
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

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
