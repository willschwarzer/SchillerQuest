package game;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ControllerInterface, Subject, Observer {
	private List<Observer> observers;
	private char[][] temp;
	private GameFrame view;
	private GameModel model;

	public Controller() {
		observers = new ArrayList<>();
	}

	public boolean addObserver(Observer o) {
		return observers.add(o);
	}

	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(temp);
		}
	}

	public void update(char[][] map) {
		this.temp = map;
	}

	public void keyAction(int key) {
		if (key == KeyEvent.VK_LEFT ||
				key == KeyEvent.VK_DOWN ||
				key == KeyEvent.VK_RIGHT ||
				key == KeyEvent.VK_UP) {
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

	public void openInventory() {
		view.displayInventory();
	}

	public void closeInventory() {
		view.closeInventory();
	}

	public void openMainScreen() {
		System.out.println("This is the main screen!");
	}

	public void openOptions() {
		System.out.println("These are the options!");
	}

	public void makeMove(int[] move) {
		Player player = model.getPlayer();
		model.moveCreature(player, move);
	}

	public void updateViewGrid(char[][] newGrid) {
		this.view.updateTextPane(newGrid);
	}

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

	public void setView(GameFrame view) {
		this.view = view;
	}

	public void setGameModel(GameModel model) {
		this.model = model;
	}
}
