package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyListener;

public class View extends JFrame implements ActionListener, KeyListener, Observer {

	// Loading levels from files and representing them as 2D arrays
	// has not been implemented yet. This code is pretty gross right now,
	// but it shows how the elements of the UI are defined.

	String level = "##################################################\n" +
				   "##                     ########\n" +
				   "#####    @   ####                         ####\n" +
				   "###                                       ####\n" +
				   "################            ########     \n" +
				   "###########\n" +
				   "###################     ###########################\n" +
				   "   ####           #    #\n" +
				   "   ####           #    #\n" +
				   "#######       #####    #\n" +
				   "#                 #    #\n" +
				   "#                 #    #\n" +
				   "#                 #    ############\n" +
				   "#                 #               #\n" +
				   "#                 #    ######     #\n" +
				   "#                 #         #     #\n" +
				   "#                 #         #     #\n" +
				   "#\n" +
				   "##################################################";

	String levelA = "##################################################\n" +
					"##                     ########                  #\n" +
					"#####";

	String levelB = "####                         ####   #\n" +
					"###                                       ####   #\n" +
					"################            ########             #\n" +
					"###########                                      #\n" +
					"###################     ##########################\n" +
					"   ####           #    #                         #\n" +
					"   ####           #    #                         #\n" +
					"#######       #####    #                         #\n" +
					"#                 #    #                         #\n" +
					"#                 #    #                         #\n" +
					"#                 #    ############              #\n" +
					"#                 #               #              #\n" +
					"#                 #    ######     #              #\n" +
					"#                 #         #     #              #\n" +
					"#                 #         #     #              #\n" +
					"#                 #         #     #              #\n" +
					"##################################################";

	String player = "    @   ";

	ControllerInterface controller = new Controller();

	public View() {
		super();
		setSize(475, 310);
		setResizable(false);
		setTitle("Game Screen");

		JTextPane tPane = new JTextPane();
		tPane.addKeyListener(this);
		tPane.setBackground(Color.BLACK);
		tPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		tPane.setForeground(Color.WHITE);
		appendToPane(tPane, levelA, Color.WHITE);
		appendToPane(tPane, player, Color.GREEN);
		appendToPane(tPane, levelB, Color.WHITE);
		add(tPane);

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
		invButton.addActionListener(this);
		JButton optionsButton = new JButton("Options");
		optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsButton.setActionCommand("options");

		// To be decided as a group whether we ultimately want these
        /*JButton buttonDefend = new JButton("Defend");
        buttonDefend.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonDefend.setActionCommand("defend");
        buttonDefend.addActionListener(this);

        JButton statsButton = new JButton("Stats");
        statsButton.setActionCommand("stats");
        statsButton.addActionListener(this);
        statsButton.setAlignmentX(Component.CENTER_ALIGNMENT);*/

		buttonPanel.add(invButton);
		buttonPanel.add(optionsButton);

		splitPanel.add(statsPanel);
		splitPanel.add(buttonPanel);
		add(splitPanel, BorderLayout.EAST);

		setLocationRelativeTo(null); // center of screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		} else {
			System.out.println("Button not yet implemented");
		}
	}

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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				View game = new View();
				game.setVisible(true);
			}
		});
	}

	public void update(char[][] newMap) {
		throw new UnsupportedOperationException("update() not yet supported for View");
	}
}
