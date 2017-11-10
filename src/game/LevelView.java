package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;

public class LevelView extends JFrame implements ActionListener, KeyListener {

	private JTextPane levelTextPane = new JTextPane();
	private ControllerInterface controller;

	public LevelView() {
		super();

		// temp implementation of loading levels from file
		File levelFile = new File("src/resources/level1.txt");
		ArrayList<ArrayList<Character>> levelGrid = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(levelFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				ArrayList<Character> chars = new ArrayList<>();
				for (char c : line.toCharArray()) {
					chars.add(c);
				}
				levelGrid.add(chars);
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		// end temp implementation

		setSize(675, 440); // TODO: dynamic resizing
		setResizable(false);
		setTitle("Game Screen");
		setupTextPane(levelGrid);
		setupSplitControlPanel();
		setLocationRelativeTo(null); // center of screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		levelTextPane.addKeyListener(this);
	}

	private void setupTextPane(ArrayList<ArrayList<Character>> charGrid) {
		drawTextOnPane(charGrid);
		levelTextPane.setEditable(false);
		levelTextPane.setBackground(Color.BLACK);
		levelTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		add(levelTextPane);
	}

	private void setupSplitControlPanel() {
		JPanel splitPanel = new JPanel();
		splitPanel.setLayout(new GridLayout(0, 1));
		splitPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
		statsPanel.add(new JLabel("Health: 100"));
		statsPanel.add(new JLabel("Magic: 13"));
		statsPanel.add(new JLabel("Luck: Low"));
		statsPanel.add(new JLabel("Action Points: 4"));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		JButton invButton = new JButton("Inventory");
		invButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		invButton.setActionCommand("inventory");
		JButton menuButton = new JButton("Options");
		menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuButton.setActionCommand("options");
		buttonPanel.add(invButton);
		buttonPanel.add(menuButton);
		invButton.addActionListener(this);
		menuButton.addActionListener(this);

		splitPanel.add(statsPanel);
		splitPanel.add(buttonPanel);
		add(splitPanel, BorderLayout.EAST);
	}

	private void drawTextOnPane(ArrayList<ArrayList<Character>> charGrid) {
		for (ArrayList<Character> row : charGrid) {
			for (char c : row) {
				appendToPane(levelTextPane, Character.toString(c), colorForChar(c));
			}
			appendToPane(levelTextPane, "\n", Color.white);
		}
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
			case 'I':
				return Color.white;
			case '|':
				return Color.white;
			case '_':
				return Color.white;
			default:
				return Color.gray;
		}
	}

	private void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet attrSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(attrSet, false);
		tp.replaceSelection(msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("inventory")) {
			controller.openInventory();
		} else if (e.getActionCommand().equals("options")){
			controller.openOptions();
		} else {
			System.out.println("Button not yet implemented");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("You pressed " + e.getKeyCode());
		controller.keyAction(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	public void setController(ControllerInterface controller) {
		this.controller = controller;
	}

}
