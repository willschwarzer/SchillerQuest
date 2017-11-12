package game;

import java.io.File;

public class Game {

	/**
	 * Instantiates Controller, Model and View and sets connections up
	 * Then displays a view of the game.
	 */
	public static void main(String[] args) {

		// TODO find a way to make this an interface again
		// (how do we access methods from both Subject and ControllerInterface???
		Controller controller = new Controller();
		GameMap map = new GameMap(new File("src/resources/level1.txt"));

		GameFrame view = new GameFrame();
		GameModel model = new GameModel();
		// Player spawning eventually should happen in the model instead
		Coordinates startCoordinates = new Coordinates(10, 3);

		// TODO get all of this initialization finished
		model.setGameMap(map);
		model.spawnPlayer(startCoordinates);
		model.setController(controller);
		model.addObserver(controller);

		controller.setView(view);
		controller.setGameModel(model);
		controller.setCharMap(map.getMapAsCharArray());
		controller.addObserver(view);
		controller.notifyObservers();

		view.setController(controller);
		view.setVisible(true);

		// The GameFrame displays the title screen
		view.displayTitle();
	}
}
