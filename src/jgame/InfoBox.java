package jgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class InfoBox {
	private final int width, height;
	private final String text;

	public InfoBox(final int height, final String text, Graphics g) {
		this.height = height;
		this.text = text;
		width = g.getFont().getWidth(text);
	}

	public int getWidgth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw(final int x, final int y, Graphics g, Color c) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		g.drawString(text, x, y + height / 2);
	}
}
