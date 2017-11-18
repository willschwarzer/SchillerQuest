package game;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryPanel extends JPanel {
	private Map<String, InventoryItem> equipped = new HashMap<>();
	private List<InventoryItem> backpack = new ArrayList<>();

	private int curr = 0;

	InventoryItemPane invItemPane = new InventoryItemPane();
	InventoryCharacterPane invCharPane = new InventoryCharacterPane();

	public InventoryPanel() {
		super();
		setLayout(new BorderLayout());

		add(invItemPane, BorderLayout.WEST);
		add(invCharPane, BorderLayout.EAST);
	}

	public void setBackpack(List newBackpack) {
		this.backpack = newBackpack;
	}

	public void setEquipped(Map<String, InventoryItem> equipped) {
		this.equipped = equipped;
	}

	public void updatePanes() {
		invItemPane.setCurr(curr);
		invItemPane.setBackpack(backpack);
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
		if (curr < backpack.size() - 1) {
			curr++;
		}
		updatePanes();
	}

	// Moves item to equipped section
	public void moveRight() {
		if (backpack.size() >= curr) { // valid change?
			String type = backpack.get(curr).getType();
			if (equipped.containsKey(type)) {
				InventoryItem oldItem = equipped.get(type);
				backpack.add(oldItem);
			}
			equipped.put(type, backpack.get(curr));
			backpack.remove(curr);
			if (curr >= backpack.size()) {
				curr = backpack.size() - 1;
			}
			if (curr < 0) {
				curr = 0;
			}
		}
		updatePanes();
	}
}
