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
public class GameFrame extends JFrame implements Observer, ActionListener {
	private LevelTextPane lvlTextPane = new LevelTextPane();
	private InventoryPane invPane = new InventoryPane();
	private TitlePane titlePane = new TitlePane();
	private JButton inventoryButton;
	private ControllerInterface controller;

	/**
	 * Creates a GameFrame object of fixed size
	 * It is able to handle key input and the top level of our view.
	 */
	public GameFrame() {
		super();
		setSize(875, 460);
		setResizable(false);
		setTitle("Schiller Quest");

		setupTextPane();
		addUIElementsToFrame();
		addBindings();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// We eventually want to switch keyboard control to the JFrame instead
		// of the text pane, but this is currently not working. ¯\_(ツ)_/¯
	}

	/**
	 * This updates the current pane with a new 2nd character array.
	 *
	 * @param newGrid The new map to be displayed
	 */
	public void update(char[][] newGrid) {
		lvlTextPane.updateCharacterGrid(newGrid);
		repaint();
	}

  /**
   * This displays the title screen of the game
   */
	public void displayTitle() {
		remove(lvlTextPane);
		add(titlePane);
		inventoryButton.setText("Start");
		revalidate();
		repaint();
	}

	/**
	 * This displays the current inventory of the player
	 */
	public void displayInventory() {
		remove(lvlTextPane);
		add(invPane);
		inventoryButton.setText("Return");
		revalidate();
		repaint();
	}

	/**
	* This displays the current level
	*/
	public void displayLevelScreen() {
		remove(invPane);
		remove(titlePane);
		// Either inventory or title screen could be present, so we remove both

		add(lvlTextPane);
		inventoryButton.setText("Inventory");
		revalidate();
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

		JButton quitButton = new JButton("Quit");
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		quitButton.addActionListener(this);
		buttonPanel.add(quitButton);

		return buttonPanel;
	}

	/**
	 * Adds key bindings to the level text pane.
	 */
	private void addBindings() {
		InputMap iMap = lvlTextPane.getInputMap(JComponent.WHEN_FOCUSED);
		InputMap iMapWindow = lvlTextPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");

		iMapWindow.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
		iMapWindow.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		iMapWindow.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
		iMapWindow.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");

		ActionMap aMap = lvlTextPane.getActionMap();

		aMap.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {-1, 0});
			}
		});
		aMap.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {0, 1});
			}
		});
		aMap.put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {1, 0});
			}
		});
		aMap.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.makeMove(new int[] {0, -1});
			}
		});

		System.out.println(lvlTextPane.getActionMap().allKeys());
	}

	/**
	 * Parses JButton input.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Inventory")) {
			controller.openInventory();
		} else if (e.getActionCommand().equals("Start") || e.getActionCommand().equals("Return")) {
			controller.openMainScreen();
		} else if (e.getActionCommand().equals("Quit")) {
			controller.quitGame();
		} else {
			System.out.println("Button not yet implemented");
		}
	}

	/*
	Standard KeyListener functions (only keyPressed is actually used at the moment)
	 */

	public void setController(ControllerInterface controller) {
		this.controller = controller;
	}
}
