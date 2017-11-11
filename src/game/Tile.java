package game;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class Tile implements MapViewable {
	private Terrain terrain;
	private Deque<Item> items;
	private Creature creature;

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

	public boolean isOccupiable() {
		// TODO Anders - come back to?
		if (creature == null && (items == null || items.isEmpty()) && terrain.isOccupiable()) {
			return true;
		} else {
			return false;
		}
	}

	public Deque<Item> getItems() {
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
		} else if (Item.class.isAssignableFrom(entity.getClass())) {
			return items.add((Item) entity);
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
		} else if (Item.class.isAssignableFrom(entity.getClass())) {
			return items.remove((Item) entity);
		} else {
			System.err.println(
					"Error, tried removing a " + entity.getClass().toString() + " from a Tile, which extends Entity "
					+ "but not Creature or Item (see about adding related handling to Tile around line " + new
							Exception().getStackTrace()[0].getLineNumber());
			return false;
		}

	}

	public Creature getCreature() {
		return creature;
	}

	/**
	 * Gets the graphic to display for the specified Tile.  Will first return the graphic of the creature on this
	 * Tile, if no creature will return the graphic of the top item on this Tile, if no items will return the graphic
	 * of the underlying Terrain.
	 *
	 * @return The graphical representation of this Tile.
	 */
	@Override
	public char getMapGraphic() {
		if (creature != null) {
			return creature.getMapGraphic();
		} else if (!items.isEmpty()) {
			return items.getFirst().getMapGraphic();
		} else {
			return terrain.getMapGraphic();
		}
	}
}
