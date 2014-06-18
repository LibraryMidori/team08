package MapHolder;

import android.content.Context;

public interface IMap {
	public void createMap(Context context);

	public void genMap(int currentRow, int currentCol);

	void genTraps(int rowClicked, int columnClicked);

	void setTheNumberOfSurroundingTrap();

	void rippleUncover(int rowClicked, int columnClicked);

	boolean isNearTheClickedCell(int rowCheck, int columnCheck, int rowClicked,
			int columnClicked);
	
	public void winGame();
	
	public void finishGame(int currentRow, int currentCol);
}
