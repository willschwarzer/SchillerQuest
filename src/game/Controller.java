package game;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Subject, Observer {
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
		openTitleScreen();

		notifyObservers();
	}

	@Override
	public boolean addObserver(Observer o) {
		return observers.add(o);
	}

	@Override
	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	/**
	 * Updates the observers' (likely just the view's) character maps
	 */
	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(charMap);
		}
	}

	/**
	 * Updates the controller with new player status information, in addition to
	 * updating the character map.
	 *
	 * @param map new character map
	 */
	@Override
	public void update(char[][] map) {
		setCharMap(map);
		if (model != null) {
			view.updateHealthDisplay(model.getPlayer().getHealth());
			view.updateLevelDisplay(model.getPlayer().getLevel());
			view.updateExpDisplay(model.getPlayer().getExp());
		}
		notifyObservers();
	}

	/**
	 * Opens the view's inventory pane.
	 */
	public void openInventory() {
		List<Item> currentInv = model.getPlayer().getBackpack();
		view.updateInventory(currentInv);
		view.displayInventory();
		model.getPlayer().setEquipped(view.updateEquipped());
		model.getPlayer().setBackpack(view.updateBackpack());
	}

	/**
	 * Opens the view's level pane.
	 */
	public void openMainScreen() {
		view.displayLevelScreen();
		List<Item> currentInv = model.getPlayer().getBackpack();
		view.updateInventory(currentInv);
		model.getPlayer().setEquipped(view.updateEquipped());
		model.getPlayer().setBackpack(view.updateBackpack());
	}

	/**
	 * Opens the view's options pane.
	 * Not yet implemented.
	 */
	public void openTitleScreen() {
		view.displayTitle();
	}

	/**
	 * When a movement key has been pressed,
	 * makes the corresponding player move in the model.
	 *
	 * @param move the direction of the desired movement
	 */
	public void makeMove(int[] move) {
		Player player = model.getPlayer();
		model.moveCreature(player, move);
		model.takeTurn();
	}

	/**
	 * Tells the view to update its activity log text.
	 * @param description the new log text
	 */
	public void log(String description) {
		view.setActivityLogText(description);
	}

	/**
	 * Picks up the item at the player's location, then takes a turn.
	 */
	public void pickUp() {
		model.pickUp();
		model.takeTurn();
	}

	/**
	 * Drops an item from the player's inventory to their location, then takes a turn.
	 * @param item item to be dropped.
	 */
	public void drop(Item item) {
		model.drop(item);
		model.takeTurn();
	}

	/**
	 * Uses a down staircase, if one is present.
	 */
	public void useDownStaircase() {
		model.useDownStaircase();
	}

	/**
	 * Uses an up staircase, if one is present.
	 */
	public void useUpStaircase() {
		model.useUpStaircase();
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
	 * It quits the game.
	 * (duh)
	 */
	public void quitGame() {
		System.exit(0);
	}

	/**
	 * Updates the character map.
	 * @param map the new character map
	 */
	public void setCharMap(char[][] map) {
		this.charMap = map;
	}
}
