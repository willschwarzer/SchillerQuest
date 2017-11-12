package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ControllerInterface, Subject, Observer {
	private List<Observer> observers;
	private GameFrame view;
	private GameModel model;
	private char[][] charMap;

	public Controller() {
		observers = new ArrayList<>();
	}

	//TODO add observer pattern functionality
	/*
	 * The next four functions are not yet used (see updateViewGrid()).
	 */
	public boolean addObserver(Observer o) {
		return observers.add(o);
	}

	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(charMap);
		}
	}

	public void update(char[][] map) {
		setCharMap(map);
		notifyObservers();
	}

	/**
	 * Handles keyboard commands passed in from the view.
	 * Currently only arrow keys are implemented.
	 *
	 * @param key
	 */
	public void keyAction(int key) {
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {
			int[] direction;
			if (key == KeyEvent.VK_LEFT) {
				direction = new int[]{-1, 0};
			} else if (key == KeyEvent.VK_DOWN) {
				direction = new int[]{0, 1};
			} else if (key == KeyEvent.VK_RIGHT) {
				direction = new int[]{1, 0};
			} else {
				direction = new int[]{0, -1};
			}
			makeMove(direction);
		} else {
			//TODO add more keys as needed
			return;
		}
	}

	/**
	 * Opens the view's inventory pane.
	 */
	public void openInventory() {
		view.displayInventory();
	}
  
  /**
   * Opens the view's level pane.
   */
	public void openMainScreen() {
		view.displayLevelScreen();

	}

	/**
	 * Opens the view's options pane.
	 * Not yet implemented.
	 */
	public void openOptions() {
		System.out.println("Not yet implemented.");
	}

	/**
	 * When a movement key has been pressed, parses the movement
	 * and makes the corresponding player move in the model.
	 *
	 * @param move
	 */
	public void makeMove(int[] move) {
		Player player = model.getPlayer();
		model.moveCreature(player, move);
	}

	/*
	The next five functions are not yet implemented.
	 */
	public void whatIsTile(Coordinates position) {
	}

	public void pickUp() {
	}

	public void drop(Item item) {
	}

	public void equip(Item item) {
	}

	public void unequip(Item item) {
	}

	/**
	 * Sets the controller's view object.
	 *
	 * @param view
	 */
	public void setView(GameFrame view) {
		this.view = view;
	}

	/**
	 * Sets the controller's model object.
	 *
	 * @param model
	 */
	public void setGameModel(GameModel model) {
		this.model = model;
	}

	 /**
	 * it quits the game
	 */
	public void quitGame() {
		System.exit(0);
	}

	public void setCharMap(char[][] map) {
		this.charMap = map;
	}
}