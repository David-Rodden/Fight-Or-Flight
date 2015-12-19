package jgame;

public enum Position {
	UP(1), LEFT(2), DOWN(3), RIGHT(4);
	private Position(final int id) {
		this.id = id;
	}

	private final int id;

	public int getId() {
		return id;
	}
}
