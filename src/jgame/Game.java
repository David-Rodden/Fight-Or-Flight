package jgame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	public static final int X_ORIG = 900, Y_ORIG = 650;
	public static int FPS = 50, X_DIM = X_ORIG, Y_DIM = Y_ORIG;

	public Game(String name) {
		super(name);
		this.addState(new Menu(GameStatus.MENU.getState()));
		this.addState(new Play(GameStatus.PLAY.getState()));
	}

	public static void main(String[] args) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Game("Fight or Flight"));
			agc.setDisplayMode(X_DIM, Y_DIM, false);
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
		this.enterState(GameStatus.MENU.getState());
	}

	public static void setWindowDimensions(final int x, final int y) {
		X_DIM = x;
		Y_DIM = y;
	}

	public static int getCorrectSizeX(final int size) {
		return X_DIM * size / X_ORIG;
	}

	public static int getCorrectSizeY(final int size) {
		return Y_DIM * size / Y_ORIG;
	}
}
