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

public class Game extends JFrame implements ActionListener, KeyListener {

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

    ControllerInterface controller = new MainScreenController();

    public Game() {
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
        JButton button1 = new JButton("Stats");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buttonInv = new JButton("inventory");
        buttonInv.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonInv.setActionCommand("inventory");
        buttonInv.addActionListener(this);
        JButton button2 = new JButton("Move");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setActionCommand("button2");
        button2.addActionListener(this);
        JButton button3 = new JButton("Attack");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton button4 = new JButton("Defend");
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.add(buttonInv);

        splitPanel.add(statsPanel);
        splitPanel.add(buttonPanel);
        add(splitPanel, BorderLayout.EAST);

        setLocationRelativeTo(null); // center of screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
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
                Game game = new Game();
                game.setVisible(true);
            }
        });
    }
}