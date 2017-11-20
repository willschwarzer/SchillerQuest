package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Item extends Entity {
	private String type;
	private String name;
	private int boost;

	/**
	 * Creates an item at given coordinates
	 *
	 * @param coordinates Coordinates the item will be created at
	 */
	public Item(Coordinates coordinates) {
		super(coordinates);
	}

	public Item(String type, int level) {
		super(new Coordinates(0, 0));
		this.type = type;
		this.boost = randomWithRange(1, level);
		this.name = makeName(type, boost);
	}

	public Item(String type, int level, String name, Coordinates coordinates) {
		super(coordinates);
		this.type = type;
		this.boost = randomWithRange(1, level);
		this.name = name;
	}

	public Item(String type, int level, String name) {
		super(new Coordinates(0, 0));
		this.type = type;
		this.boost = randomWithRange(1, level);
		this.name = name;
	}

	@Override
	public String getInfo() {
		return null;
	}

	private String makeName(String type, int boost) {
		String name = "+" + boost + " " + type;
		return name;
	}

	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		int value = (int) (Math.random() * range) + min;
		return value;
	}

	public int getBoost() {
		return boost;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public char getMapGraphic() {
		char graphic = '*';
		if (Objects.equals(this.type, "weapon")) {
			graphic = '△';
		} else if (Objects.equals(this.type, "shield")) {
			graphic = '▯';
		} else if (Objects.equals(this.type, "amulet")) {
			graphic = '◎';
		} else if (Objects.equals(this.type, "armor")) {
			graphic = '೧';
		} else if (Objects.equals(this.type, "shoes")) {
			graphic = '◣';
		}
		return graphic;
	}


	public static List<Item> getAppropriateItems(Random random, int difficulty) {
		List<Item> items = new ArrayList<>();

		for (int i = 0; i < random.nextInt((int) Math.sqrt(difficulty) + 1); i++) {
			items.add(createItem(random, difficulty));
		}

		return items;
	}

	public static Item createItem(Random random, int difficulty) {
		List<String> itemTypes = new ArrayList<>();
		itemTypes.add("weapon");
		itemTypes.add("shield");
		itemTypes.add("amulet");
		itemTypes.add("armor");
		itemTypes.add("shoes");

		String type = itemTypes.get(random.nextInt(itemTypes.size()));

		Item rand = new Item(type, random.nextInt(difficulty) + 1);

		return rand;
	}
}
