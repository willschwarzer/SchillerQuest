package game;

import java.io.File;

public class Game {
	public static void main(String[] args) {
		ControllerInterface controller = new Controller();
		LevelView view = new LevelView();
		GameMap map = new GameMap(new File("src/resources/level1.txt"));
		GameModel model = new GameModel();

		// TODO get all of this initialization finished
		controller.setView(view);
		controller.setGameModel(model);

		view.setController(controller);
		view.setVisible(true);

		model.setGameMap(map);
	}
}
