package game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

/**
 * An implementation of Java's JFrame with a purpose of displaying a window we can put elements in.
 * It is able to handle key input and is the top level of our view.
 */
public class GameFrame extends JFrame implements Observer, ActionListener {
	private JPanel levelAndLogPanel = new JPanel();
	private LevelTextPane lvlTextPane = new LevelTextPane();
	private InventoryPanel invPanel = new InventoryPanel();
	private JLabel activityLog = new JLabel();

	private TitlePane titlePane = new TitlePane();
	private JButton inventoryButton;
	private Controller controller;
	private static final boolean IS_WINDOWS = System.getProperty("os.name").contains("Windows");

	/**
	 * Creates a GameFrame object of fixed size
	 * It is able to handle key input and the top level of our view.
	 */
	public GameFrame(Controller controller) {
		super();

		if (IS_WINDOWS) {
			setSize(930, 520);
		} else {
			setSize(930, 435);
		}
		setResizable(false);
		setTitle("Schiller Quest");
		this.controller = controller;
		controller.addObserver(this);
		setupUIElements();
		addBindings();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This updates the current pane with a new 2nd character array.
	 *
	 * @param newGrid The new map to be displayed
	 */
	@Override
	public void update(char[][] newGrid) {
		lvlTextPane.updateCharacterGrid(newGrid);
		repaint();
	}

	/**
	 * This displays the title screen of the game
	 */
	public void displayTitle() {
		remove(levelAndLogPanel);
		add(titlePane);
		inventoryButton.setText("Start");
		revalidate();
		repaint();
	}

	public void updateInventory(List newInv) {
		invPanel.setBackpack(newInv);
	}

	public List<InventoryItem> updateBackpack() {
		return invPanel.getBackpack();
	}

	public Map<String, InventoryItem> updateEquipped() {
		return invPanel.getEquipped();
	}

	/**
	 * This displays the current inventory of the player
	 */
	public void displayInventory() {
		remove(levelAndLogPanel);
		invPanel.updatePanes();
		add(invPanel);
		inventoryButton.setText("Return");
		revalidate();
		repaint();
	}

	/**
	 * This displays the current level
	 */
	public void displayLevelScreen() {
		remove(invPanel);
		remove(titlePane);
		// Either inventory or title screen could be present, so we remove both

		add(levelAndLogPanel);
		inventoryButton.setText("Inventory");
		revalidate();
		repaint();
	}

	/**
	 * Update the text in the activity log
	 */
	public void setActivityLogText(String text) {
		activityLog.setText(text);
	}

	private void setupUIElements() {
		setupTextPaneAndLog();
		setupSplitControlPanel();
	}

	private void setupTextPaneAndLog() {
		levelAndLogPanel = new JPanel();
		levelAndLogPanel.setLayout(new BorderLayout());

		activityLog.setOpaque(true);
		activityLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		activityLog.setBackground(Color.black);
		activityLog.setForeground(Color.white);
		activityLog.setText("");

		levelAndLogPanel.add(lvlTextPane, BorderLayout.CENTER);
		levelAndLogPanel.add(activityLog, BorderLayout.PAGE_END);
		add(levelAndLogPanel);
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
		statsPanel.add(new JLabel("Level: 1"));
		statsPanel.add(new JLabel("XP: 13/100"));
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
		addKeyBinding(lvlTextPane, KeyEvent.VK_LEFT, "left", (action) -> controller.makeMove(new int[]{-1, 0}));
		addKeyBinding(lvlTextPane, KeyEvent.VK_DOWN, "down", (action) -> controller.makeMove(new int[]{0, 1}));
		addKeyBinding(lvlTextPane, KeyEvent.VK_RIGHT, "right", (action) -> controller.makeMove(new int[]{1, 0}));
		addKeyBinding(lvlTextPane, KeyEvent.VK_UP, "up", (action) -> controller.makeMove(new int[]{0, -1}));
		addKeyBinding(lvlTextPane, KeyEvent.VK_D, "downStaircase", (action) -> controller.useDownStaircase());
		addKeyBinding(lvlTextPane, KeyEvent.VK_U, "upStaircase", (action) -> controller.useUpStaircase());

		invPanel.addBindingsToChildren();
	}

	public static void addKeyBinding(JComponent component, int key, String id, ActionListener action) {
		InputMap iMap = component.getInputMap(JComponent.WHEN_FOCUSED);
		InputMap iMapAncestor = component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		InputMap iMapWindow = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = component.getActionMap();

		iMap.put(KeyStroke.getKeyStroke(key, 0, false), id);
		iMapAncestor.put(KeyStroke.getKeyStroke(key, 0, false), id);
		iMapWindow.put(KeyStroke.getKeyStroke(key, 0, false), id);
		aMap.put(id, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(e);
			}
		});
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
		requestFocusInWindow();
	}
}
