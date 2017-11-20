package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryPanel extends JPanel {
	private Map<String, Item> equipped = new HashMap<>();
	private List<Item> backpack = new ArrayList<>();
	private Controller controller;

	private int curr = 0;

	private InventoryItemPane invItemPane = new InventoryItemPane();
	private InventoryCharacterPane invCharPane = new InventoryCharacterPane();

	public InventoryPanel(Controller controller) {
		super();
		this.controller = controller;
		setLayout(new BorderLayout());

		add(invItemPane, BorderLayout.WEST);
		add(invCharPane, BorderLayout.EAST);
	}

	public List<Item> getBackpack() {
		return backpack;
	}

	public void setBackpack(List<Item> newBackpack) {
		this.backpack = newBackpack;
	}

	public Map<String, Item> getEquipped() {
		return equipped;
	}

	public void setEquipped(Map<String, Item> equipped) {
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
				Item oldItem = equipped.get(type);
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

	public void drop() {
		controller.drop(backpack.get(curr));
		curr = Math.max(0, curr-1);
		updatePanes();
	}

	protected void addBindingsToChildren() {
		GameFrame.addKeyBinding(invItemPane, KeyEvent.VK_UP, "up", (action) -> selectUp());
		GameFrame.addKeyBinding(invItemPane, KeyEvent.VK_DOWN, "down", (action) -> selectDown());
		GameFrame.addKeyBinding(invItemPane, KeyEvent.VK_RIGHT, "right", (action) -> moveRight());
		GameFrame.addKeyBinding(invItemPane, KeyEvent.VK_D, "drop", (action) -> drop());

		GameFrame.addKeyBinding(invCharPane, KeyEvent.VK_UP, "up", (action) -> selectUp());
		GameFrame.addKeyBinding(invCharPane, KeyEvent.VK_DOWN, "down", (action) -> selectDown());
		GameFrame.addKeyBinding(invCharPane, KeyEvent.VK_RIGHT, "right", (action) -> moveRight());
		GameFrame.addKeyBinding(invCharPane, KeyEvent.VK_D, "drop", (action) -> drop());
	}
}
