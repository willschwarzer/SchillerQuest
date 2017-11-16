package game;

public class Game {

	/**
	 * Instantiates a Controller, which takes care of the rest.
	 */
	public static void main(String[] args) {
		// TODO find a way to make this an interface again
		// (how do we access methods from both Subject and ControllerInterface???
		Controller controller = new Controller();
		MapGenerator myGenerator = new MapGenerator();
		GameMap myMap = myGenerator.generate(1);
	}
}
