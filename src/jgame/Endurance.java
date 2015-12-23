package jgame;

public class Endurance {
	private static int energy, speed;

	static {
		energy = 100;
		speed = 1;
	}

	public static int getEnergy() {
		return energy;
	}

	public static int getSpeed() {
		System.out.println(speed);
		return 32 % speed != 0 ? speed - 1 : speed;
	}

	public static void deductEnergy() {
		energy--;
	}

	public static void increaseSpeed() {
		if (speed < 10) speed++;
	}

	public static void normalizeSpeed() {
		if (speed > 1) speed--;
	}
}
