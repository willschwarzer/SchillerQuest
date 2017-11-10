package game;

import javax.swing.*;

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

	void updateViewGrid(char[][] newGrid);

	void setView(GameFrame view);

	void setGameModel(GameModel model);
}
