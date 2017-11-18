package game;

public interface ControllerInterface {
	void openInventory();

	void openMainScreen();

	void openOptions();

	void makeMove(int[] move);

	void whatIsTile(Coordinates position);

	void pickUp();

	void drop(GraphicItem item);

	void equip(GraphicItem item);

	void unequip(GraphicItem item);

	void setView(GameFrame view);

	void setGameModel(GameModel model);

	void setCharMap(char[][] map);

	void quitGame();
}
