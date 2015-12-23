package jgame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	public static int FPS = 50;

	public Game(String name) {
		super(name);
		this.addState(new Menu(GameStatus.MENU.getState()));
		this.addState(new Play(GameStatus.PLAY.getState()));
	}

	public static void main(String[] args) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Game("Fight or Flight"));
			agc.setDisplayMode(1000, 720, false);
			agc.setTargetFrameRate(FPS);
			agc.setVSync(true);
			agc.setAlwaysRender(true);
			agc.start();
		} catch (SlickException e) {
			System.out.println("An unexpected error was encountered...");
			e.printStackTrace();
		}
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		final GameStatus[] gs = GameStatus.values();
		for (int i = 0; i < gs.length; i++)
			this.getState(gs[i].getState()).init(gc, this);
		this.enterState(GameStatus.PLAY.getState());
	}
}
