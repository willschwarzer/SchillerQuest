package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameFrame extends JFrame implements ActionListener, KeyListener {

	private LevelTextPane lvlTextPane;

	public GameFrame() {
		super();
		setSize(675, 440);
		setResizable(false);
		setTitle("Game Screen Sample Text");

		setupTextPane();
		addUIElementsToFrame();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addUIElementsToFrame() {
		setupTextPane();
		setupSplitControlPanel();
	}

	private void setupTextPane() {
		ArrayList<ArrayList<Character>> level = readFromFile();
		lvlTextPane = new LevelTextPane(level);
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
		JButton button1 = new JButton("Inventory");
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button1.addActionListener(this);
		buttonPanel.add(button1);
		return buttonPanel;
	}


	private ArrayList<ArrayList<Character>> readFromFile() {
		// temp implementation of loading levels from file
		File levelFile = new File("level1.txt");
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
		return levelGrid;
		// end temp implementation
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Button not yet implemented");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		return;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameFrame game = new GameFrame();
				game.setVisible(true);
			}
		});
	}
}