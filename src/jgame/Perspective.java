package jgame;

import org.newdawn.slick.tiled.TiledMap;

public class Perspective {
	private final TiledMap map;
	private final int x, y;

	public Perspective(final TiledMap map, final int x, final int y) {
		this.map = map;
		this.x = x;
		this.y = y;
	}

	public TiledMap getMap() {
		return map;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
