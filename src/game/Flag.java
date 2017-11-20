package game;

/**
 * Temporary implementation of an Item for testing with.
 */
public class Flag extends GraphicItem {
	public Flag() {
		super(new Coordinates(0, 0));
	}

	public Flag(Coordinates coordinates) {
		super(coordinates);
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public char getMapGraphic() {
		return '⚑';
	}
}
