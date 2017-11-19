package game;

import java.util.ArrayList;
import java.util.List;

public class Controller implements ControllerInterface, Subject, Observer {
	private List<Observer> observers;
	private GameFrame view;
	private GameModel model;
	private char[][] charMap;

	public Controller() {
		observers = new ArrayList<>();
		view = new GameFrame(this);
		model = new GameModel(this);

		view.setVisible(true);
		// The GameFrame displays the title screen
		view.displayTitle();

		notifyObservers();
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
	 * Opens the view's inventory pane.
	 */
	public void openInventory() {
		List currentInv = model.getPlayer().getBackpack();
		view.updateInventory(currentInv);
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
		model.takeTurn();
	}

	/*
	The next five functions are not yet implemented.
	 */
	public void whatIsTile(Coordinates position) {
	}

	public void pickUp() {
	}

	public void drop(GraphicItem item) {
	}

	public void equip(GraphicItem item) {
	}

	public void unequip(GraphicItem item) {
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
