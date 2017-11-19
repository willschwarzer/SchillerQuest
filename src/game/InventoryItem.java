package game;

public class InventoryItem {
	private String type;
	private String name;
	private int boost;

	public InventoryItem(String type, int boost){
		this.type = type;
		this.boost = boost;
		this.name = makeName(type, this.boost);

	}

	public InventoryItem(String type, int level, String name){
		this.type = type;
		this.boost = randomWithRange(1,level);
		this.name = name;
	}

	private String makeName(String type, int boost){
		 String name = type + " +" + boost;
		 return name;
	}

	private int randomWithRange(int min, int max)
	{
		int range = (max - min) + 1;
		int value = (int)(Math.random() * range) + min;
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
=======
	private String type;
	private String name;
	private int boost;

	public InventoryItem(String type, int level) {
		this.type = type;
		this.boost = level;
		this.name = makeName(type, boost);

	}

	public InventoryItem(String type, int level, String name) {
		this.type = type;
		this.boost = randomWithRange(1, level);
		this.name = name;
	}

	private String makeName(String type, int boost) {
		String name = type + " +" + boost;
		return name;
	}

	public int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
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
}
