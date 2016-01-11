package jgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Interface {
	public static final int INVENTORY = 0;

	private static int currentFocus = 0;
	private static Image display;
	static {
		try {
			display = new Image("res/screen.png");
		} catch (SlickException e) {
			System.out.println("Error: Unable to find screen-image in directory.");
			e.printStackTrace();
		}
	}

	public static void switchFocus(final int focus) {
		currentFocus = focus;
	}

	public static void switchInventory() {
		switchFocus(INVENTORY);
	}

	public static void display(final int width, final int height) {
		display.draw(0, 0, width, height);
	}

	public static void draw(final Graphics g, final Inventory inventory) {
		final int xPosition = (int) (Math.pow(789, 2) / (float) Game.X_DIM), yPosition = (int) (Math.pow(476, 2) / (float) Game.Y_DIM);
		switch (currentFocus) {
		case INVENTORY:
			inventory.draw(g, Game.getCorrectSizeX(xPosition), Game.getCorrectSizeY(yPosition), Game.getCorrectSizeX(50));
			break;
		}

	}
}
