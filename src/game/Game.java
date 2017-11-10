package game;

import java.io.File;

public class Game {
	public static void main(String[] args) {
		ControllerInterface controller = new Controller();
//		LevelView view = new LevelView();
		GameFrame view = new GameFrame();
		GameMap map = new GameMap(new File("src/resources/level1.txt"));
		GameModel model = new GameModel();
		Coordinates startCoordinates = new Coordinates(10, 3);

		// TODO get all of this initialization finished
		model.setGameMap(map);
		model.spawnPlayer(startCoordinates);

		controller.setView(view);
		controller.setGameModel(model);

		view.setController(controller);
		view.setVisible(true);

	}
}
