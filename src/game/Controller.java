package game;

import java.util.ArrayList;

public class Controller implements ControllerInterface, Subject, Observer {
	private ArrayList observers;
	private char[][] temp;

	public void addObserver(Observer o){

		observers.add(o);
	}

	public void removeObserver(Observer o){
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++){
			Observer obs = (Observer)observers.get(i);
			obs.update(temp);
		}
	}

	public void update(char[][] map) {
		this.temp = map;
	}

	public void keyAction(int key) {
		System.out.println("You pressed " + key);
	}

	public void openInventory() {
		System.out.println("This is your inventory!");
	}

	public void openMainScreen() {
		System.out.println("This is the main screen!");
	}

	public void openOptions() {
		System.out.println("These are the options!");
	}

	public void makeMove(int[] move) {

	}

	public void attack(Entity attacker, Entity attackee) {

	}

	public void whatIsTile(Coordinates position) {

	}

	public void pickUp() {

	}

	public void drop(Item item) {

	}
}
