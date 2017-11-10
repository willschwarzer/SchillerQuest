package game;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class Tile implements MapViewable {
	private Terrain terrain;
	private Deque<Entity> entities;
	private Creature creature;

	public Tile(Terrain terrain) {
		this.terrain = terrain;
		this.entities = new LinkedList<>();
	}

	public Tile(Terrain terrain, Entity entity) {
		this.terrain = terrain;
		this.entities = new LinkedList<>();
		this.entities.addFirst(entity);
	}

	public Tile(Terrain terrain, Collection<Entity> entities) {
		this.terrain = terrain;
		this.entities = new LinkedList<>(entities);
	}

	public boolean isOccupiable() {
		// TODO ANders - come back to?
		if (creature == null && (entities == null || entities.isEmpty()) && terrain.isOccupiable()) {
			return true;
		} else {
			return false;
		}
	}

	public Deque<Entity> getEntities() {
		return entities;
	}

	public boolean addEntity(Entity entity) {
		if (entity.getClass() == Creature.class) {
			if (this.creature == null) {
				this.creature = (Creature) entity;
			} else {
				throw new IllegalStateException("Error: tile cannot have two entities");
			}
		}
		return this.entities.add(entity);
	}

	public boolean removeEntity(Entity entity) {
		if (entity == creature) {
			this.creature = null;
		}
		return this.entities.remove(entity);
	}

	public Creature getCreature() {
		return creature;
	}

	@Override
	public char getMapGraphic() {
		if (entities.isEmpty()) {
			return terrain.getMapGraphic();
		} else {
			Entity top = entities.getFirst();
			return top.getMapGraphic();
		}
	}
}
