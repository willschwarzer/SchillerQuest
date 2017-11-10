package game;

public abstract class Entity implements MapViewable {
	Coordinates coordinates;
	boolean isOccupiable;
	char graphic;

	abstract public String getInfo();

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public char getMapGraphic() {
		return graphic;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isOccupiable() {
		return isOccupiable;
	}
}
