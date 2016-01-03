package jgame;

import org.newdawn.slick.Image;

public class Item {
	public static final int UNDEFINED = -1;
	private final int id;
	private final String name;
	private final boolean isStackable;
	private Image icon;

	public Item(final int id, final String name, final boolean isStackable) {
		this.id = id;
		this.name = name;
		this.isStackable = isStackable;
		final String directory = "res/items/";
		try {
			icon = new Image(directory + id + ".png");
		} catch (Exception e) {
			try {
				System.out.println("Error: Unable to find icon in directory.");
				icon = new Image(directory + "undefined.png");
			} catch (Exception e1) {

				System.out.println("Error: Error receiving info from directory.");
				e1.printStackTrace();
			}
		}
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

	public Image getIcon() {
		return icon;
	}
}
