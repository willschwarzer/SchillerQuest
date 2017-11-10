package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * An implementation of Java's JFrame with a purpose of displaying a window we can put elements in.
 * It is able to handle key input and is the top level of our view.
 */
public class GameFrame extends JFrame implements ActionListener, KeyListener {
	private LevelTextPane lvlTextPane = new LevelTextPane();
	private InventoryPane invPane = new InventoryPane();
	private JButton inventoryButton;
	private ControllerInterface controller;

	/**
	 * Creates a GameFrame object of fixed size
	 * It is able to handle key input and the top level of our view.
	 */
	public GameFrame() {
		super();
		setSize(675, 440);
		setResizable(false);
		setTitle("GameFrame");

		setupTextPane();
		addUIElementsToFrame();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// We eventually want to switch keyboard control to the JFrame instead
		// of the text pane, but this is currently not working
		lvlTextPane.addKeyListener(this);

	}

	/**
	 * This updates the current pane with a new 2nd character array.
	 *
	 * @param newGrid The new map to be displayed
	 */
	public void updateTextPane(char[][] newGrid) {
		lvlTextPane.updateCharacterGrid(newGrid);
		repaint();
	}

	/**
	 * This displays the current inventory of the player
	 */
	public void displayInventory() {
		remove(lvlTextPane);
		add(invPane);
		inventoryButton.setText("Return");
		validate();
		repaint();
	}

	/**
	 * This closes the display of inventory
	 */
	public void openMainScreen() {
		remove(invPane);
		add(lvlTextPane);
		inventoryButton.setText("Inventory");
		validate();
		repaint();
	}

	private void addUIElementsToFrame() {
		setupTextPane();
		setupSplitControlPanel();
	}

	private void setupTextPane() {
		lvlTextPane = new LevelTextPane();
		add(lvlTextPane);
	}

	private void setupSplitControlPanel() {
		JPanel splitPanel = new JPanel();
		splitPanel.setLayout(new GridLayout(0, 1));
		splitPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		splitPanel.add(buildStatsPanel());
		splitPanel.add(buildButtonPanel());
		add(splitPanel, BorderLayout.EAST);
	}

	private JPanel buildStatsPanel() {
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
		//TODO: make these accessible and editable
		statsPanel.add(new JLabel("Health: 100"));
		statsPanel.add(new JLabel("Magic: 13"));
		statsPanel.add(new JLabel("Luck: Low"));
		statsPanel.add(new JLabel("Action Points: 4"));
		return statsPanel;
	}

	private JPanel buildButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		inventoryButton = new JButton("Inventory");
		inventoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		inventoryButton.addActionListener(this);
		buttonPanel.add(inventoryButton);
		return buttonPanel;
	}

	/**
	 * Parses JButton input.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Inventory")) {
			controller.openInventory();
		} else if (e.getActionCommand().equals("Return")) {
			controller.openMainScreen();
		} else {
			System.out.println("Button not yet implemented");
		}
	}

	/*
	Standard KeyListener functions (only keyPressed is actually used at the moment.)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
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
