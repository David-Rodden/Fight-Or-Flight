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
	private int width, height, centerX, centerY, collisionLayer, overLayer;
	private LocalPlayer user;

	public Play(final int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/mapTest.tmx");
		collisionLayer = map.getLayerIndex("Collision");
		overLayer = map.getLayerIndex("Over");
		width = map.getTileWidth();
		height = map.getTileHeight();
		user = new LocalPlayer(5, 5, map);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		user.draw(gc, g, collisionLayer, overLayer);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		pause(gc, 2);
		user.update(gc, collisionLayer);
	}

	@Override
	public int getID() {
		return state;
	}

	private void pause(GameContainer gc, final int ticks) {
		gc.sleep(ticks * 5);
	}
}
