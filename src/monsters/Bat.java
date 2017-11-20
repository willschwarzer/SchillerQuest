package monsters;
import game.*;


import java.util.HashMap;


public class Bat extends Monster {

	public Bat(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
//		setMapGraphic('b');
		HashMap<String, InventoryItem> equipped = new HashMap<>();
		equipped.put("weapon", new InventoryItem("weapon", level, "Bat Claws"));
		equipped.put("armor",new InventoryItem("armor", level, "Bat Wings "));
		setEquipped(equipped);
		setName("Lvl " + level + " Bat");
	}

	public String getInfo(){
		return "null";
	}

	@Override
	public char getMapGraphic() {
		return 'b';
	}
}
