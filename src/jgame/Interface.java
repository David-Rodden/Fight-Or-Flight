package jgame;

import org.newdawn.slick.Graphics;

public class Interface {
	public static final int INVENTORY = 0;

	private static int currentFocus = 0;

	public static void switchFocus(final int focus) {
		currentFocus = focus;
	}

	public static void switchInventory() {
		switchFocus(INVENTORY);
	}

	public static void draw(final Graphics g, final Inventory inventory) {
		final int xPosition = 750, yPosition = 400;
		switch (currentFocus) {
		case INVENTORY:
			inventory.draw(g, xPosition, yPosition, 50);
			break;
		}

	}
}
