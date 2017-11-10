package game;

public class Player extends Creature {

	public Player(Coordinates coordinates) {
		super(coordinates);
	}

	@Override
	public String getInfo() {
		throw new UnsupportedOperationException("getInfo() not yet supported for Player");
	}

	@Override
	public char getMapGraphic() {
		return '@';
		// TODO FIX THIS
	//	throw new UnsupportedOperationException("getMapGraphic() not yet supported for Player");
	}
}
