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
	@Override
	public boolean addObserver(Observer o) {
		return observers.add(o);
	}

	@Override
	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(charMap);
		}
	}

	@Override
	public void update(char[][] map) {
		setCharMap(map);
		notifyObservers();
	}

	/**
	 * Opens the view's inventory pane.
	 */
	@Override
	public void openInventory() {
		List currentInv = model.getPlayer().getBackpack();
		view.updateInventory(currentInv);
		view.displayInventory();
		model.getPlayer().setEquipped(view.updateEquipped());
		model.getPlayer().setBackpack(view.updateBackpack());
	}

	/**
	 * Opens the view's level pane.
	 */
	@Override
	public void openMainScreen() {
		view.displayLevelScreen();
		List currentInv = model.getPlayer().getBackpack();
		view.updateInventory(currentInv);
		model.getPlayer().setEquipped(view.updateEquipped());
		model.getPlayer().setBackpack(view.updateBackpack());
	}

	/**
	 * Opens the view's options pane.
	 * Not yet implemented.
	 */
	@Override
	public void openOptions() {
		System.out.println("Not yet implemented.");
	}

	/**
	 * When a movement key has been pressed, parses the movement
	 * and makes the corresponding player move in the model.
	 *
	 * @param move
	 */
	@Override
	public void makeMove(int[] move) {
		Player player = model.getPlayer();
		model.moveCreature(player, move);
		model.takeTurn();
	}

	/**
	 * Tell the view to update its activity log text
	 */
	@Override
	public void log(String description) {
		view.setActivityLogText(description);
	}

	/*
	The next five functions are not yet implemented.
	 */
	@Override
	public void whatIsTile(Coordinates position) {
	}

	@Override
	public void pickUp() {
	}

	@Override
	public void drop(InventoryItem item) {
	}

	@Override
	public void equip(InventoryItem item) {
	}

	@Override
	public void unequip(InventoryItem item) {
	}

	/**
	 * Sets the controller's view object.
	 *
	 * @param view
	 */
	@Override
	public void setView(GameFrame view) {
		this.view = view;
	}

	/**
	 * Sets the controller's model object.
	 *
	 * @param model
	 */
	@Override
	public void setGameModel(GameModel model) {
		this.model = model;
	}

	/**
	 * it quits the game
	 */
	@Override
	public void quitGame() {
		System.exit(0);
	}

	@Override
	public void setCharMap(char[][] map) {
		this.charMap = map;
	}
}
