package game;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class Tile implements MapViewable {
	private Terrain terrain;
	private Deque<InventoryItem> items;
	private Creature creature;
	private boolean seen;
	private boolean visible;

	/**
	 * Creates a Tile with the given Terrain.
	 *
	 * @param terrain Terrain to have for the Tile
	 */
	public Tile(Terrain terrain) {
		this.terrain = terrain;
		this.items = new LinkedList<>();
	}


	/**
	 * Creates a Tile with the given Terrain and Entity occupying the tile.
	 *
	 * @param terrain Terrain to have for the Tile
	 * @param entity  Entity to initialize the Tile with
	 */
	public Tile(Terrain terrain, Entity entity) {
		this.terrain = terrain;
		this.items = new LinkedList<>();
		addEntity(entity);
	}

	/**
	 * Creates a Tile with the given Terrain and Collection of Entitys
	 *
	 * @param terrain  Terrain to have for the Tile
	 * @param entities Collection of type Entity to add to the Tile
	 */
	public Tile(Terrain terrain, Collection<Entity> entities) {
		this.terrain = terrain;
		this.items = new LinkedList<>();
		for (Entity entity : entities) {
			addEntity(entity);
		}
	}

	public boolean isOccupiableTerrain() {
		return terrain.isOccupiable();
	}

	/**
	 * Checks whether the Tile is occupiable and has no Creature or Items on it.
	 *
	 * @return Whether the Tile is occupiable and empty.
	 */
	public boolean isOccupiableAndEmpty() {
		return (creature == null && (items == null || items.isEmpty()) && terrain.isOccupiable());
	}

	public Deque<InventoryItem> getItems() {
		return items;
	}

	/**
	 * Attempts to add an Entity to the Tile.  Can only add a Creature if there is no Creature already on the Tile.
	 * Currently this does not add objects that extend Entity, but do not extend not either Creature or Item.  If this
	 * is needed, add appropriate handling.
	 *
	 * @param entity Entity to add to the Tile.  Must extend either Creature or Item.
	 * @return Whether the Entity was successfully added to the Tile.
	 */
	public boolean addEntity(Entity entity) {
		if (Creature.class.isAssignableFrom(entity.getClass())) {
			if (this.creature == null) {
				this.creature = (Creature) entity;
				return true;
			} else {
				throw new IllegalStateException("Error: tile cannot have two entities");
			}
		} else if (InventoryItem.class.isAssignableFrom(entity.getClass())) {
			return items.add((InventoryItem) entity);
		} else {
			System.err.println(
					"Error, tried adding a " + entity.getClass().toString() + " to a Tile, which extends Entity but "
					+ "not Creature or Item (see about adding related handling to Tile around line " + new Exception()
							.getStackTrace()[0].getLineNumber());
			return false;
		}
	}

	/**
	 * Remove an Entity from the Tile.
	 *
	 * @param entity Entity to remove from the tile
	 * @return Whether the Entity was removed successfully.
	 */
	public boolean removeEntity(Entity entity) {
		if (Creature.class.isAssignableFrom(entity.getClass())) {
			if (entity == creature) {
				creature = null;
				return true;
			} else {
				return false;
			}
		} else if (InventoryItem.class.isAssignableFrom(entity.getClass())) {
			return items.remove((InventoryItem) entity);
		} else {
			System.err.println(
					"Error, tried removing a " + entity.getClass().toString() + " from a Tile, which extends Entity "
					+ "but not Creature or Item (see about adding related handling to Tile around line " + new
							Exception().getStackTrace()[0].getLineNumber());
			return false;
		}
	}

	public boolean getSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public Creature getCreature() {
		return creature;
	}

	/**
	 * Gets the graphic to display for the specified Tile.  If the Tile is visible, it will first return the graphic
	 * of the creature on this Tile, if no creature will return the graphic of the top item on this Tile, if no items
	 * will return the graphic of the underlying Terrain.  If the Tile is not visible, but has been seen, it will
	 * return the graphic of the underlying Terrain.  If the Tile has not been seen, it will return Tile
	 * .getUnknownTerrainGraphic()
	 *
	 * @return The graphical representation of this Tile.
	 */
	@Override
	public char getMapGraphic() {
		if (visible) {
			if (creature != null) {
				return creature.getMapGraphic();
			} else if (!items.isEmpty()) {
				return items.getFirst().getMapGraphic();
			} else {
				return terrain.getMapGraphic();
			}
		} else if (seen) {
			return terrain.getMapGraphic();
		} else {
			return Terrain.getUnknownTerrainGraphic();
		}
	}

	public static boolean markTileVisiblity(Tile[][] array, boolean visible) {
		for (Tile[] row : array) {
			for (Tile tile : row) {
				try {
					tile.setVisible(visible);
				} catch (NullPointerException ignored) {
				}
			}
		}
		return true;
	}

	public static boolean markTileSeen(Tile[][] array, boolean seen) {
		for (Tile[] row : array) {
			for (Tile tile : row) {
				try {
					tile.setSeen(seen);
				} catch (NullPointerException ignored) {
				}
			}
		}
		return true;
	}
}
