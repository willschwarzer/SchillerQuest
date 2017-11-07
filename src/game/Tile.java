package game;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class Tile implements MapViewable {
	private Terrain terrain;
	private Deque<Entity> entities;

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
		throw new UnsupportedOperationException("isOccupiable() not yet implemented for Tile");
		// TODO figure out how to handle this - allow being on top of certain terrain, items, but not other terrain &
		// other creatures
	}

	public Deque<Entity> getEntities() {
		return entities;
	}

	public boolean addEntity(Entity entity) {
		return this.entities.add(entity);
	}

	public boolean removeEntity(Entity entity) {
		return this.entities.remove(entity);
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
