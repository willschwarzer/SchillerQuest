package game;

public class Terrain implements MapViewable {
	private String id; // TODO replace with enum or something?
	private char mapGraphic;
	private boolean isOccupiable;

	/**
	 * Creates a Terrain with a given char as the id & mapGraphic.
	 *
	 * @param ch Both the id & mapGraphic of the Terrain
	 */
	public Terrain(char ch) {
		this.id = Character.toString(ch); // TODO handle better?
		this.mapGraphic = ch;
		this.isOccupiable = Terrain.checkIfOccupiable(id);
	}

	/**
	 * Creates a Terrain with the given id & mapGraphic
	 *
	 * @param id         The id of the Terrain
	 * @param mapGraphic The mapGraphic of the Terrain
	 */
	public Terrain(String id, char mapGraphic) {
		if (Terrain.checkIfValidID(id)) {
			this.id = id;
			this.mapGraphic = mapGraphic;
			this.isOccupiable = Terrain.checkIfOccupiable(this.id);
		} else {
			throw new IllegalArgumentException("Cannot use empty String for Terrain id value");
			// TODO handle differently?
		}
	}

	/**
	 * Check if the Terrain can be occupied by an Entity.
	 *
	 * @return Whether the Terrain can be occupied
	 */
	public boolean isOccupiable() {
		return isOccupiable;
	}

	@Override
	public char getMapGraphic() {
		return mapGraphic;
	}

	private static boolean checkIfValidID(String id) {
		// TODO reformat to work with texture pack
		if (id.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	private static boolean checkIfOccupiable(String id) {
		if (id.equals(" ") || id.equals("$") || id.equals("I")) {
			return true;
		} else {
			return false;
		}
	}
}
