package jgame;

public class Slot {
	private Item item;
	private int amount;

	public Slot(final int itemId) {
		this(itemId, 1);
	}

	public Slot(final int itemId, final int amount) {
		this.item = Items.getItem(itemId);
		this.amount = amount;
	}

	public Item getItem() {
		return item;
	}

	public int getAmount() {
		return amount;
	}

	public void addAmount(final int amount) {
		this.amount += amount;
	}

	public void subAmount(final int amount) {
		this.amount -= amount;
	}
}
