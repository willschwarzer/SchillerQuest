package game;

public interface ControllerInterface {
	void openInventory();

	void openMainScreen();

	void openOptions();

	void makeMove(int[] move);

	void whatIsTile(Coordinates position);

	void pickUp();

	void drop(InventoryItem item);

	void equip(InventoryItem item);

	void unequip(InventoryItem item);

	void setView(GameFrame view);

	void setGameModel(GameModel model);

	void setCharMap(char[][] map);

	void quitGame();
}
