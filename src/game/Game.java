package game;

import java.io.File;

public class Game {
	public static void main(String[] args) {
		ControllerInterface controller = new Controller();
		LevelView view = new LevelView();
		GameMap map = new GameMap(new File("level1.txt"));

		// TODO get all of this initialization finished
		controller.setView(view);
		controller.setGameMap(map);
		view.setVisible(true);
	}
}
