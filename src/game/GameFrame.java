package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements ActionListener, KeyListener {

	private LevelTextPane lvlTextPane;
	private ControllerInterface controller;

	public GameFrame() {
		super();
		setSize(675, 440);
		setResizable(false);
		setTitle("Game Screen Sample Text");

		setupTextPane();
		addUIElementsToFrame();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lvlTextPane.addKeyListener(this);
	}

	private void addUIElementsToFrame() {
		setupTextPane();
		setupSplitControlPanel();
	}

	private void setupTextPane() {
//		ArrayList<ArrayList<Character>> level = readFromFile();

		lvlTextPane = new LevelTextPane();
		add(lvlTextPane);
	}

	public void updateTextPane(char[][] newGrid) {
		lvlTextPane.updateCharacterGrid(newGrid);
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
		JButton button1 = new JButton("Inventory");
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button1.addActionListener(this);
		buttonPanel.add(button1);
		return buttonPanel;
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