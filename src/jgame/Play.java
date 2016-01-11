package jgame;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState {
	private final int state;
	private TiledMap map;
	private int width, height, centerX, centerY, collisionLayer, mouseX, mouseY;
	private int[] overLayers;
	private LocalPlayer user;

	public Play(final int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/mapTest.tmx");
		collisionLayer = map.getLayerIndex("Collision");
		overLayers = new int[] { map.getLayerIndex("Over"), map.getLayerIndex("OverB") };
		width = map.getTileWidth();
		height = map.getTileHeight();
		try {
			user = new LocalPlayer(18, 5, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		user.draw(gc, g, collisionLayer, overLayers);
		Interface.display(gc.getWidth(), gc.getHeight());
		user.drawStats(g);
		final Input input = gc.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		g.drawString("Pos: (" + mouseX + ", " + mouseY + ")", mouseX + 5, mouseY - 20);
		Tiles.getTileAt(map, input).draw(g);
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
