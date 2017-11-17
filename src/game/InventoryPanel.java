package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPanel extends JPanel {
	private ArrayList<String> backpack = new ArrayList<>();
	private ArrayList<String> equipped = new ArrayList<>();
	private int curr = 0;

	InventoryItemPane invItemPane = new InventoryItemPane();
	InventoryCharacterPane invCharPane = new InventoryCharacterPane();

	public InventoryPanel() {
		super();
		setLayout(new BorderLayout());

		//**
		//TODO: all of this should happen in model
		//TODO: these items need to have types
		backpack.add("Cool Sword");
		backpack.add("Rusty Spoon");
		backpack.add("Freedom Key");
		backpack.add("another");
		backpack.add("an item!");
		backpack.add("wow one more");
		//**

		invItemPane.setBackpack(backpack);
		invItemPane.setEquipped(equipped);
		updatePanes();
		add(invItemPane, BorderLayout.WEST);
		add(invCharPane, BorderLayout.EAST);
	}

	private void updatePanes() {
		invItemPane.setBackpack(backpack);
		invItemPane.setEquipped(equipped);
		invItemPane.setCurr(curr);
		invItemPane.drawPane();
		invCharPane.drawPane();
	}

	public void selectUp() {
		if (curr > 0) {
			curr--;
		}
		updatePanes();
	}

	public void selectDown() {
		if (curr < backpack.size() - 1 || curr < equipped.size() - 1) {
			curr++;
		}
		updatePanes();
	}

	//TODO: move this to model?
	// Moves item to backpack section
	public void moveLeft() {
		if (equipped.size() >= curr) { // valid change?
			backpack.add(equipped.get(curr));
			equipped.remove(curr);
			if (curr >= backpack.size() && curr >= equipped.size())
				curr = equipped.size() - 1;
			if (curr < 0)
				curr = 0;
		}
		updatePanes();
	}

	// Moves item to equipped section
	public void moveRight() {
		if (backpack.size() >= curr) { // valid change?
			equipped.add(backpack.get(curr));
			backpack.remove(curr);
			if (curr >= backpack.size() && curr >= equipped.size())
				curr = backpack.size() - 1;
			if (curr < 0)
				curr = 0;
		}
		updatePanes();
	}
}
