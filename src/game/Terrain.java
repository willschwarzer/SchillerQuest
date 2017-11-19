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
		this.isOccupiable = checkIfOccupiable(this.id);
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
			this.isOccupiable = checkIfOccupiable(this.id);
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
		if (id.equals(" ") || id.equals("•") || id.equals("$") || id.equals("I")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to get standard graphical representation of a Terrain that would be outside the GameMap bounds.
	 * (Checked when a Terrain/Tile does not exist.)
	 *
	 * @return The graphic for a Terrain that is outside the GameMap boundary.
	 */
	public static char getOutOfWorldTerrainGraphic() {
		return ' ';
		// May want to return '␀' (reads NUL) to represent out of world stuff while testing/debugging
	}

	/**
	 * Method to get standard graphical representation of a Terrain that is in the GameMap bounds, but not currently
	 * known/visible.
	 *
	 * @return The graphic for a Terrain that is not known/visible.
	 */
	public static char getUnknownTerrainGraphic() {
		return '?';
	}
}
