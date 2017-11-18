package game;

/**
 * Temporary implementation of a Monster for testing.
 */
public class Zombie extends Monster {
	public Zombie() {
		super(new Coordinates(0,0));
	}
	public Zombie(Coordinates coordinates) {
		super(coordinates);
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public char getMapGraphic() {
		return 'Z';
	}
}
