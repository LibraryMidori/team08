package GameRule;

import CellPackage.Cell;

public interface IGameStrategy {
	public void gameControl(int level, int currentScore, int lives);

	public void setUpGame(int playTime, int traps, int currentScore, int lives);

	public void startGame();

	public boolean checkGameWin(Cell cell);

	public void finishGame(int currentRow, int currentCol);

	public void winGame();

	public void rippleUncover(int rowClicked, int columnClicked);
}
