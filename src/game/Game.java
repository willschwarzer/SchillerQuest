package game;

import java.io.File;

public class Game {

	/**
	 * Instantiate Controller, Model and View and sets connections up
	 * Then displays a view of the game.
	 */
	public static void main(String[] args) {

		ControllerInterface controller = new Controller();
		GameMap map = new GameMap(new File("src/resources/level1.txt"));
		GameFrame view = new GameFrame();
		GameModel model = new GameModel();
		Coordinates startCoordinates = new Coordinates(10, 3);

		// TODO get all of this initialization finished
		model.setGameMap(map);
		model.spawnPlayer(startCoordinates);
		model.setController(controller);

		controller.setView(view);
		controller.setGameModel(model);
		controller.updateViewGrid(map.getMapAsCharArray());

		view.setController(controller);
		view.setVisible(true);
	}
}
