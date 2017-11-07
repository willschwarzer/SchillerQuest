package game;

public class Terrain implements MapViewable {
	private String name;
	private boolean isPassable;
	private char mapGraphic;

	public boolean isPassable() {
		return isPassable;
	}

	@Override
	public char getMapGraphic() {
		return mapGraphic;
	}
}
