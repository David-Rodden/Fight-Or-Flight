package jgame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class LocalPlayer {
	private SpriteSheet something;
	private Image[] position;
	private Animation[] walking;
	private int x, y, rX, rY, pos, tileWidth, tileHeight;
	private Animation currAnim;
	private final TiledMap map;

	public LocalPlayer(final int x, final int y, final TiledMap map) throws SlickException {
		something = new SpriteSheet("res/something.png", 64, 64);
		this.map = map;
		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();
		walking = new Animation[4];
		position = new Image[walking.length];
		for (int i = 0; i < walking.length; i++) {
			Image[] frames = new Image[9];
			for (int j = 0; j < frames.length; j++)
				frames[j] = something.getSubImage(j, 8 + i);
			position[i] = frames[0];
			walking[i] = new Animation(frames, 100);
		}
		currAnim = walking[0];
		currAnim.setAutoUpdate(false);
	}

	public void update(GameContainer gc, final int collisionLayer) {
		final int currX = x * tileWidth, currY = y * tileHeight;
		if (rX != currX || rY != currY) {
			final int frame = currAnim.getFrame();
			if (rX % 4 == 2 || rY % 4 == 2) currAnim.setCurrentFrame(frame == currAnim.getFrameCount() - 1 ? 0 : frame + 1);
			if (rX < currX)
				rX += 2;
			else if (rX > currX)
				rX -= 2;
			else if (rY < currY)
				rY += 2;
			else
				rY -= 2;
			return;
		}
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_UP) && isWalkable(map, collisionLayer, x, y - 1)) {
			y--;
			pos = 0;
		} else if (input.isKeyDown(Input.KEY_LEFT) && isWalkable(map, collisionLayer, x - 1, y)) {
			x--;
			pos = 1;
		} else if (input.isKeyDown(Input.KEY_DOWN) && isWalkable(map, collisionLayer, x, y + 1)) {
			y++;
			pos = 2;
		} else if (input.isKeyDown(Input.KEY_RIGHT) && isWalkable(map, collisionLayer, x + 1, y)) {
			x++;
			pos = 3;
		} else if (currAnim != null && currAnim.getFrame() != 0) {
			currAnim.setCurrentFrame(0);
		}
		currAnim = walking[pos];
	}

	public void draw(GameContainer gc, Graphics g, final int collisionLayer, final int overLayer) {
		int centerX = gc.getWidth() / (tileWidth * 2);
		int centerY = gc.getHeight() / (tileHeight * 2);
		final int layers = map.getLayerCount(), mapX = (-rX + centerX * tileWidth) + getPositionWidth() / 4, mapY = (-rY + centerY * tileHeight)
				+ getPositionHeight() / 2;
		for (int i = 0; i < layers; i++) {
			if (i != collisionLayer && i != overLayer) map.render(mapX, mapY, i);
		}
		getCurrentPosition().draw(centerX * tileWidth, centerY * tileHeight);
		map.render(mapX, mapY, overLayer);
		g.drawString("x: " + x + ", y: " + y, 100, 10);
	}

	public int getX() {
		return rX;
	}

	public int getY() {
		return rY;
	}

	public Image getCurrentPosition() {
		return currAnim.getCurrentFrame();
	}

	public int getPositionWidth() {
		return currAnim.getWidth();
	}

	public int getPositionHeight() {
		return currAnim.getHeight();
	}

	private boolean isWalkable(final TiledMap map, final int collisionLayer, final int x, final int y) {
		final boolean walkable = (x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight()) && map.getTileId(x, y, collisionLayer) == 0;
		if (walkable) {
		}
		return walkable;
	}
}
