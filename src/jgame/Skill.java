package jgame;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Skill {
	private long experience;
	private int level;
	private String name;

	public Skill(final String name) {
		this(name, 0);
	}

	public Skill(final String name, final long experience) {
		this.experience = experience;
		this.name = name;
		level = checkLevel();
	}

	public static void main(String[] args) {
		Skill s = new Skill("test");
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		for (int i = 0; i <= 280; i += 10)
			System.out.println("Level " + i + ": " + df.format(s.getExperienceAt(i)));

	}

	public long getExperience() {
		return experience;
	}

	public long getExperienceAt(final int level) {
		long experience = 0;
		for (int i = 1; i < level; i++)
			experience += (3 + 5 * i + Math.E * Math.pow(2, i / 5)) * 10;
		return experience;
	}

	public int getLevel() {
		return level;
	}

	public void updateExperience(final long experience) {
		this.experience += experience;
		level = checkLevel();
	}

	public String getName() {
		return name;
	}

	private int checkLevel() {
		long experience = 0;
		for (int i = 1;; i++) {
			experience += (3 + 5 * i + Math.E * Math.pow(2, i / 5)) * 10;
			if (experience > this.experience) return i;
		}
	}
}
