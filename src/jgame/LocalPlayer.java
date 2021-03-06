package jgame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class LocalPlayer {
	private final int NAKED = 0, GLOVES = 1;

	private SpriteSheet[] characterSprites;
	private Animation[][] equipment;
	private Animation[] walking, gloveAnim;
	private int rX, rY, pos, tileWidth, tileHeight, numDraw;
	private Animation[] currAnim;
	private final TiledMap map;
	private Skills skills;
	private int health;
	private Tile currentTile;
	private int alpha, max;
	private boolean isHurt;
	private int gain;
	private LevelUp lu;
	private Inventory inventory;
	private Perspective perspective;

	public LocalPlayer(final int x, final int y, final TiledMap map) throws SlickException, IOException {
		File charFolder = new File("res/sprites/local_char");
		Path[] p = Files.list(charFolder.toPath()).toArray(Path[]::new);
		characterSprites = new SpriteSheet[p.length];
		for (int i = 0; i < characterSprites.length; i++)
			characterSprites[i] = new SpriteSheet(p[i].toUri().toURL(), 64, 64);
		skills = new Skills();
		health = skills.getHealth().getLevel();
		this.map = map;
		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();
		walking = new Animation[4];
		gloveAnim = new Animation[4];
		equipment = new Animation[characterSprites.length][4];
		currAnim = new Animation[characterSprites.length];
		for (int i = 0; i < equipment.length; i++) {
			for (int j = 0; j < equipment[i].length; j++) {
				Image[] frames = new Image[9];
				for (int k = 0; k < frames.length; k++) {
					frames[k] = characterSprites[i].getSubImage(k, 8 + j);
				}
				equipment[i][j] = new Animation(frames, 100);

			}
			currAnim[i] = equipment[i][0];
			currAnim[i].setAutoUpdate(false);
		}
		currentTile = new Tile(map, x, y);
		rX = x * tileWidth;
		rY = y * tileHeight;
		alpha = 0;
		max = 255;
		inventory = new Inventory(1, 2);
		numDraw = 4;
	}

	public void update(GameContainer gc, final int collisionLayer) {
		final int alphaAmt = 15;
		if (isHurt && alpha > 0) {
			if (alpha >= max)
				isHurt = false;
			else
				alpha += alpha + alphaAmt >= max ? max - alpha : alphaAmt;
		} else if (!isHurt && alpha > 0) alpha -= alpha < alphaAmt ? alpha : alphaAmt;
		int currX = currentTile.getX(), currY = currentTile.getY();
		if (rX != currX || rY != currY) {
			if (currAnim.length == 0) return;
			final int frame = currAnim[0].getFrame();
			int speed = 1;
			if (rX % (4 * speed) == 2 * speed || rY % (4 * speed) == 2 * speed) for (int i = 0; i < currAnim.length; i++)
				currAnim[i].setCurrentFrame(frame == currAnim[i].getFrameCount() - 1 ? 0 : frame + 1);
			if (rX < currX)
				rX += 2 * speed;
			else if (rX > currX)
				rX -= 2 * speed;
			else if (rY < currY)
				rY += 2 * speed;
			else
				rY -= 2 * speed;
			return;
		}
		currX = currentTile.getTileX();
		currY = currentTile.getTileY();
		Input input = gc.getInput();
		// keys for testing purposes to see if item-spawning works with
		// inventory
		if (input.isKeyPressed(Input.KEY_8))
			numDraw++;
		else if (input.isKeyPressed(Input.KEY_9) && numDraw > 1) numDraw--;
		if (input.isKeyPressed(Input.KEY_BACK)) inventory.removeItem(2);
		final int[] keys = { Input.KEY_0, Input.KEY_1, Input.KEY_2, Input.KEY_3, Input.KEY_4, Input.KEY_5, Input.KEY_6, Input.KEY_7 };
		for (int i = 0; i < keys.length; i++)
			if (input.isKeyPressed(keys[i])) inventory.addItems(i, 100000);
		if (input.isKeyDown(Input.KEY_UP)) {
			if (isWalkable(map, collisionLayer, currX, currY - 1)) currentTile.alterY(-1);
			pos = 0;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			if (isWalkable(map, collisionLayer, currX - 1, currY)) currentTile.alterX(-1);
			pos = 1;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			if (isWalkable(map, collisionLayer, currX, currY + 1)) currentTile.alterY(1);
			pos = 2;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			if (isWalkable(map, collisionLayer, currX + 1, currY)) currentTile.alterX(1);
			pos = 3;
		} else if (currAnim != null) {
			for (int i = 0; i < currAnim.length; i++)
				if (currAnim[i] == null || currAnim[i].getFrame() == 0) return;
			for (int i = 0; i < currAnim.length; i++)
				currAnim[i].setCurrentFrame(0);
		}
		for (int i = 0; i < currAnim.length; i++)
			currAnim[i] = equipment[i][pos];
		if (gain != 0) {
			lu = new LevelUp(gc, gain, "Endurance");
			gain = 0;
		}
	}

	public void draw(GameContainer gc, Graphics g, final int collisionLayer, final int[] overLayers) {
		int centerX = gc.getWidth() / (tileWidth * 2);
		int centerY = gc.getHeight() / (tileHeight * 2);
		final int layers = map.getLayerCount(), mapX = (-rX + centerX * tileWidth) + getPositionWidth() / 4, mapY = (-rY + centerY * tileHeight)
				+ getPositionHeight() / 2;
		for (int i = 0; i < layers; i++) {
			if (i != collisionLayer && Arrays.binarySearch(overLayers, i) < 0) map.render(mapX, mapY, i);
		}
		getCurrentPosition().forEach(p -> p.draw(centerX * tileWidth, centerY * tileHeight, new Color(255, 255 - alpha, 255 - alpha, 255)));
		for (final int i : overLayers)
			map.render(mapX, mapY, i);
		g.setColor(Color.red);
		g.fillRoundRect(750, 10, 200, 15, 5);
		g.setColor(Color.green);
		final Skill healthSkill = skills.getHealth();
		g.fillRoundRect(750, 10, 200 * health / healthSkill.getLevel(), 15, 5);
		g.setColor(Color.white);
		g.drawRoundRect(750, 10, 200, 15, 5);
		g.setColor(Color.blue);
		g.drawString(health + " / " + healthSkill.getLevel(), 830, 9);
		g.setColor(Color.white);
		final Skill endurance = skills.getEndurance();
		final int lvl = endurance.getLevel();
		g.drawString("Endurance:\nLevel:" + lvl + "\nXp:" + endurance.getExperience() / 10 + "/" + endurance.getExperienceAt(lvl + 1) / 10, 100, 10);
		if (lu != null && lu.getHeight() > 0) lu.draw(g);
		g.setColor(Color.yellow);
	}

	public void drawStats(final Graphics g) {
		Interface.draw(g, inventory);
	}

	public int getX() {
		return rX;
	}

	public int getY() {
		return rY;
	}

	public List<Image> getCurrentPosition() {
		final Image[] positions = new Image[currAnim.length];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = currAnim[i].getCurrentFrame();
		}
		return Arrays.asList(positions);
	}

	public int getPositionWidth() {
		return currAnim != null && currAnim.length > 0 ? currAnim[0].getWidth() : -1;
	}

	public int getPositionHeight() {
		return currAnim != null && currAnim.length > 0 ? currAnim[0].getHeight() : -1;
	}

	private boolean isWalkable(final TiledMap map, final int collisionLayer, final int x, final int y) {
		final boolean walkable = (x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight()) && map.getTileId(x, y, collisionLayer) == 0;
		if (walkable) {
			gain = skills.getEndurance().updateExperience(1);
		}
		return walkable;
	}

	private void hurt(final int damage) {
		health -= damage >= health ? health : damage;
		if (!isHurt) isHurt = true;
		if (alpha <= 0) alpha = 1;
	}
}
