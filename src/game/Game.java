package game;

import java.io.File;

public class Game {

	/**
	 * Instantiates Controller, Model and View and sets connections up
	 * Then displays a view of the game.
	 */
	public static void main(String[] args) {

		ControllerInterface controller = new Controller();
		GameMap map = new GameMap(new File("src/resources/level1.txt"));
		GameFrame view = new GameFrame();
		GameModel model = new GameModel();
		// Player spawning eventually should happen in the model instead
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
