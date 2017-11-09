package game;

public class Controller implements ControllerInterface {
	private LevelView view;
	private GameModel model;

	public void keyAction(int key) {
		if (key >= 37 && key <= 40) {
			int[] direction = new int[2];
			if (key == 37) {
				direction = new int[]{-1, 0};
			} else if (key == 38) {
				direction = new int[]{0, -1};
			} else if (key == 39) {
				direction = new int[]{1, 0};
			} else {
				direction = new int[]{0, 1};
			}
			makeMove(direction);
		} else {
			System.out.println("Key not yet implemented.");
		}
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
		Player player = model.getPlayer();
		model.moveCreature(player, move);
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

	public void setView(LevelView view) {
		this.view = view;
	}

	public void setGameModel(GameModel model) {
		this.model = model;
	}
}
