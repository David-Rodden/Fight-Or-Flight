package jgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState {
	private final int state;
	private Image menuPic, titlePic, startPic, toolsPic, startPicHover, toolsPicHover, cursor;
	private MouseOverArea startButton, toolsButton;
	private Sound openSound;

	public Menu(final int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, final StateBasedGame sbg) throws SlickException {
		menuPic = new Image("res/castle_ruins.jpg").getScaledCopy(gc.getHeight() / 670f);
		titlePic = new Image("res/Fight-Or-Flight.png");
		startPic = new Image("res/Play.png");
		toolsPic = new Image("res/Tools.png");
		startPicHover = new Image("res/Play-Hover.png");
		toolsPicHover = new Image("res/Tools-Hover.png");
		cursor = new Image("res/Sword-Cursor.png");
		openSound = new Sound("res/sounds/doorOpen_2.ogg");
		startButton = new MouseOverArea(gc, startPic, gc.getWidth() / 2 - startPic.getWidth() / 2, 250, new ComponentListener() {

			@Override
			public void componentActivated(AbstractComponent ac) {
				openSound.play();
				sbg.enterState(GameStatus.PLAY.getState(), new FadeOutTransition(Color.black, 1500), new FadeInTransition(Color.black, 1500));
			}
		});
		startButton.setMouseOverImage(startPicHover);
		toolsButton = new MouseOverArea(gc, toolsPic, gc.getWidth() / 2 - toolsPic.getWidth() / 2, 400, new ComponentListener() {

			@Override
			public void componentActivated(AbstractComponent ac) {
				sbg.enterState(GameStatus.MENU.getState());
			}
		});
		toolsButton.setMouseOverImage(toolsPicHover);
		gc.setMouseCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(menuPic, 0, 50f);
		g.drawImage(titlePic, 50, 40);
		startButton.render(gc, g);
		toolsButton.render(gc, g);
		if (startButton.isMouseOver()) {
			final Input input = gc.getInput();
			new InfoBox(100, "This is my info-box", g).draw(input.getMouseX(), input.getMouseY(), g, Color.blue);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
	}

	@Override
	public int getID() {
		return state;
	}

}
