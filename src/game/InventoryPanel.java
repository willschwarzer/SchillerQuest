package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryPanel extends JPanel {
	private ArrayList<String> backpackItems = new ArrayList<>();
	private ArrayList<String> backpackItemTypes = new ArrayList<>();

	private Map<String, String> equipped = new HashMap<>();
	private int curr = 0;

	InventoryItemPane invItemPane = new InventoryItemPane();
	InventoryCharacterPane invCharPane = new InventoryCharacterPane();

	public InventoryPanel() {
		super();
		setLayout(new BorderLayout());

		//**
		//TODO: all of this should happen in model
		backpackItems.add("Cool Sword");
		backpackItems.add("Rusty Spoon");
		backpackItems.add("another");
		backpackItems.add("Modest Loins");
		backpackItems.add("wow one more");

		backpackItemTypes.add("weapon");
		backpackItemTypes.add("weapon");
		backpackItemTypes.add("shield");
		backpackItemTypes.add("armor");
		backpackItemTypes.add("amulet");
		//**

		invItemPane.setBackpack(backpackItems, backpackItemTypes);
		updatePanes();
		add(invItemPane, BorderLayout.WEST);
		add(invCharPane, BorderLayout.EAST);
	}

	private void updatePanes() {
		invItemPane.setBackpack(backpackItems, backpackItemTypes);
		invItemPane.setCurr(curr);
		invItemPane.drawPane();

		invCharPane.setSelectedType(invItemPane.getSelectedType());
		invCharPane.setEquippedItems(equipped);
		invCharPane.drawPane();
	}

	public void selectUp() {
		if (curr > 0) {
			curr--;
		}
		updatePanes();
	}

	public void selectDown() {
		if (curr < backpackItems.size() - 1) {
			curr++;
		}
		updatePanes();
	}
	
	// Moves item to equipped section
	public void moveRight() {
		if (backpackItems.size() >= curr) { // valid change?
			String type = backpackItemTypes.get(curr);
			if (equipped.containsKey(type)) {
				String oldItem = equipped.get(type);
				backpackItems.add(oldItem);
				backpackItemTypes.add(type);
			}
			equipped.put(type, backpackItems.get(curr));
			backpackItems.remove(curr);
			backpackItemTypes.remove(curr);
			if (curr >= backpackItems.size())
				curr = backpackItems.size() - 1;
			if (curr < 0)
				curr = 0;
		}
		System.out.println(equipped);
		updatePanes();
	}
}
