package jgame;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class LevelUp {
	private int gain;
	private final String skill;
	private final int width, startHeight;
	private int height;
	private TrueTypeFont font;

	public LevelUp(GameContainer gc, final int gain, final String skill) {
		this.gain = gain;
		this.skill = skill;
		width = gc.getWidth() / 2;
		startHeight = height = gc.getHeight() / 3;
		font = new TrueTypeFont(new Font("Comic Sans MS", Font.BOLD, 25), true);
	}

	public void draw(final Graphics g) {
		int alpha = (int) (height / (float) startHeight * 255);
		final String output = "You've gained " + gain + " " + skill + (gain == 1 ? " level!" : " levels!");
		font.drawString(width - font.getWidth(output) / 2, height -= 3, output, new Color(255, 255, 255, alpha));
	}

	public int getHeight() {
		return height;
	}
}
