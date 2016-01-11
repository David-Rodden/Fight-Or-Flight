package jgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class Tile {
	private int x, y, dimension;

	public Tile(final TiledMap map, final int x, final int y) {
		dimension = map.getTileWidth();
		this.x = x;
		this.y = y;
	}

	public int getTileX() {
		return x;
	}

	public int getX() {
		return x * dimension;
	}

	public void alterX(final int dx) {
		x += dx;
	}

	public int getTileY() {
		return y;
	}

	public int getY() {
		return y * dimension;
	}

	public void alterY(final int dy) {
		y += dy;
	}

	public void draw(final Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x * dimension, y * dimension, dimension, dimension);
	}
}
