package jgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Inventory {
	public final static int SLOTS = 20, ITEM_NOT_FOUND = -1, SLOTS_PER_ROW = 4;
	private Slot[] slots;

	public Inventory(final int... items) {
		slots = new Slot[20];
		final int invSize = items.length > SLOTS ? SLOTS : items.length;
		int i;
		for (i = 0; i < invSize; i++)
			slots[i] = new Slot(i);
		for (; i < SLOTS; i++)
			slots[i] = new Slot(Item.UNDEFINED);
	}

	public int hasItem(final int id) {
		for (int i = 0; i < SLOTS; i++) {
			final Item currentItem = slots[i].getItem();
			if (currentItem != null && currentItem.getId() == id) return i;
		}
		return ITEM_NOT_FOUND;
	}

	public void addItem(final int id) {
		addItems(id, 1);
	}

	public void addItems(final int id, final int amount) {
		for (int i = 0; i < SLOTS; i++) {
			final Item current = slots[i].getItem();
			if (current != null && current.getId() == id && current.isStackable()) {
				slots[i].addAmount(amount);
				return;
			}
		}
		for (int i = 0; i < SLOTS; i++) {
			if (slots[i].getItem() == null) {
				slots[i] = new Slot(id, amount);
				break;
			}
		}
	}

	public void removeItem(final int id) {
		removeItems(id, 1);
	}

	public void removeItems(final int id, final int amount) {
		for (int i = 0; i < SLOTS; i++) {
			final Item current = slots[i].getItem();
			if (current != null && current.getId() == id) {
				final int currentAmount = slots[i].getAmount();
				if (current.isStackable() && amount < currentAmount) {
					slots[i].subAmount(amount);
					return;
				}
				slots[i] = new Slot(Item.UNDEFINED);
				return;
			}
		}
	}

	public void draw(final Graphics g, final int x, final int y, final int dimension) {
		for (int i = 0, col = 0, row = 0; i < SLOTS; i++) {
			if (row == SLOTS_PER_ROW) {
				col++;
				row = 0;
			}
			final int dX = row * dimension, dY = col * dimension;
			g.setColor(new Color(142, 90, 38, 155));
			g.fillRect(x + dX, y + dY, dimension, dimension);
			g.setColor(Color.yellow);
			g.drawRect(x + dX, y + dY, dimension, dimension);
			final Item currentItem = slots[i].getItem();
			if (currentItem != null) {
				final Image icon = currentItem.getIcon();
				if (icon != null) icon.draw(x + dX, y + dY, dimension, dimension);
				if (currentItem.isStackable()) {
					final String amount = String.valueOf(slots[i].getAmount());
					g.drawString(amount, x + dX + dimension - g.getFont().getWidth("8") * amount.length(), y + dY);
				}
			}
			row++;
		}

	}
}
