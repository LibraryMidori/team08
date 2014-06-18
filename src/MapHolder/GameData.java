package MapHolder;

public class GameData {
	private static GameData gameData = new GameData();
	private boolean isGameStart;
	private boolean isMapGen;
	private int level;
	private int lives;
	private int trapsRemain;
	private int totalScore;
	private boolean isWinGame;
	private boolean isGameOver;
	private boolean isGameFinish;

	private GameData() {

	}

	public static GameData getInstance() {
		return gameData;
	}

	public boolean isGameStart() {
		return isGameStart;
	}

	public void setGameStart(boolean isGameStart) {
		this.isGameStart = isGameStart;
	}

	public boolean isMapGen() {
		return isMapGen;
	}

	public void setMapGen(boolean isMapGen) {
		this.isMapGen = isMapGen;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getTrapsRemain() {
		return trapsRemain;
	}

	public void setTrapsRemain(int trapsRemain) {
		this.trapsRemain = trapsRemain;
	}

	public boolean isWinGame() {
		return isWinGame;
	}

	public void setWinGame(boolean isWinGame) {
		this.isWinGame = isWinGame;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean isGameFinish() {
		return isGameFinish;
	}

	public void setGameFinish(boolean isGameFinish) {
		this.isGameFinish = isGameFinish;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
