package jgame;

import org.newdawn.slick.tiled.TiledMap;

public class Movement {
	private final int key, collisionLayer, dx, dy;
	private final TiledMap map;

	public Movement(final int key, final int dx, final int dy, final TiledMap map, final int collisionLayer) {
		this.key = key;
		this.dx = dx;
		this.dy = dy;
		this.map = map;
		this.collisionLayer = collisionLayer;
	}

	// public boolean isActionPermitted(GameContainer gc, final int x, final int
	// y) {
	// return gc.getInput().isKeyDown(key) && isWalkable(map, collisionLayer, x
	// + dx, y + dy);
	// }

}
