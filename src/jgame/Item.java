package jgame;

public class Item {
	private final int id;
	private final String name;
	private final boolean isStackable;

	public Item(final int id, final String name, final boolean isStackable) {
		this.id = id;
		this.name = name;
		this.isStackable = isStackable;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isStackable() {
		return isStackable;
	}
}
