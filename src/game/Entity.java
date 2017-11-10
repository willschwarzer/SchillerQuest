package game;

public abstract class Entity implements MapViewable {
	Coordinates coordinates;
	boolean isOccupiable;
	char graphic;

	abstract public String getInfo();

	public Entity(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public char getMapGraphic() {
		return graphic;
	}


	public void setCoordinates(Coordinates coor) {
		coordinates = coor;
	}

	public boolean isOccupiable() {
		return isOccupiable;
	}
}
