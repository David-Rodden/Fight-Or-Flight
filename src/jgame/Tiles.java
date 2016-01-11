package jgame;

import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

public class Tiles {
	public static Tile getTileAt(final TiledMap map, final Input input) {
		return new Tile(map, input.getMouseX() / map.getTileWidth(), input.getMouseY() / map.getTileHeight());
	}
}
