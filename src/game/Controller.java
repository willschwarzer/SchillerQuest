package game;

public class Controller implements ControllerInterface {
	private View view;
	private GameMap map;

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

	public void setView(View view) {
		this.view = view;
	}

	public void setGameMap(GameMap map) {
		this.map = map;
	}
}
