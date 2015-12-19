package jgame;

public enum GameStatus {
	MENU(0), PLAY(1);

	GameStatus(final int state) {
		this.state = state;
	}

	private final int state;

	public int getState() {
		return state;
	}
}
