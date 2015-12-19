package jgame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState {
	private final int state;
	private TiledMap map;
	private int x, y, width, height, centerX, centerY, pos, collisionLayer;
	private SpriteSheet something;
	private Image[] position;
	private Animation[] walking;
	private int dx, dy;
	private Animation currAnim;

	public Play(final int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/mapTest.tmx");
		collisionLayer = map.getLayerIndex("Collision");
		width = map.getTileWidth();
		height = map.getTileHeight();
		something = new SpriteSheet("res/something.png", 64, 64);
		walking = new Animation[4];
		position = new Image[walking.length];
		for (int i = 0; i < walking.length; i++) {
			Image[] frames = new Image[9];
			for (int j = 0; j < frames.length; j++)
				frames[j] = something.getSubImage(j, 8 + i);
			position[i] = frames[0];
			walking[i] = new Animation(frames, 100);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		currAnim = walking[pos];
		currAnim.setAutoUpdate(false);
		centerX = gc.getWidth() / (width * 2);
		centerY = gc.getHeight() / (height * 2);
		final int layers = map.getLayerCount();
		for (int i = 0; i < layers; i++) {
			if (i != collisionLayer)
				map.render((-x + centerX) * width + currAnim.getWidth() / 4 - dx, (-y + centerY) * height + currAnim.getHeight() / 2 - dy, i);
		}
		currAnim.getCurrentFrame().draw(centerX * width, centerY * height);
		g.drawString("x: " + x + ", y: " + y, 100, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_UP) && isWalkable(map, x, y - 1)) {
			y--;
			pos = 0;
		} else if (input.isKeyDown(Input.KEY_LEFT) && isWalkable(map, x - 1, y)) {
			x--;
			pos = 1;
		} else if (input.isKeyDown(Input.KEY_DOWN) && isWalkable(map, x, y + 1)) {
			y++;
			pos = 2;
		} else if (input.isKeyDown(Input.KEY_RIGHT) && isWalkable(map, x + 1, y)) {
			x++;
			pos = 3;
		} else if (currAnim != null && currAnim.getFrame() != 0) {
			currAnim.setCurrentFrame(0);
		}
		pause(gc, 20);
	}

	@Override
	public int getID() {
		return state;
	}

	private boolean isWalkable(final TiledMap map, final int x, final int y) {
		final boolean walkable = (x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight()) && map.getTileId(x, y, collisionLayer) == 0;
		if (walkable) {
			final int frame = currAnim.getFrame();
			currAnim.setCurrentFrame(frame == currAnim.getFrameCount() - 1 ? 0 : frame + 1);
		}
		return walkable;
	}

	private void pause(GameContainer gc, final int ticks) {
		gc.sleep(ticks * 5);
	}
}
