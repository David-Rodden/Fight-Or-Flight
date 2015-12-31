package jgame;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	public final static int SLOTS = 20;
	private List<Slot> slots;

	public Inventory(final int... items) {
		this.slots = new ArrayList<Slot>();
		final int invSize = items.length > SLOTS ? SLOTS : items.length;
		for (int i = 0; i < invSize; i++) {
			slots.add(new Slot(i));
		}
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public void addItem(final int id) {
		addItems(id, 1);
	}

	public void addItems(final int id, final int amount) {
		final int invSize = slots.size();
		for (int i = 0; i < invSize; i++) {
			final Item current = slots.get(i).getItem();
			if (current.getId() == id && current.isStackable()) {
				slots.get(i).addAmount(amount);
				return;
			}
		}
		if (invSize < SLOTS) slots.add(new Slot(id, amount));
	}
}
