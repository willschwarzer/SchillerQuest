package game;

public interface ControllerInterface {
	void keyAction(int key);

	void openInventory();

	void openMainScreen();

	void openOptions();

	void makeMove(int[] move);

	void whatIsTile(Coordinates position);

	void pickUp();

	void drop(Item item);

	void equip(Item item);

	void unequip(Item item);

	void setView(GameFrame view);

	void setGameModel(GameModel model);

	void setCharMap(char[][] map);

	void quitGame();
}
