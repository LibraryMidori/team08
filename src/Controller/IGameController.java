package Controller;

import MapHolder.IMap;
import PrivateData.PrivateDataQuizGame;

public interface IGameController {
	public IMap gameController(int _level,
			PrivateDataQuizGame quizData);

	public void onClickOnCellHandle(int currentRow, int currentColumn);

	public boolean onLongClickOnCellHandle(int currentRow, int currentColumn);

	public void flagAndDoubtHandle(int currentRow, int currentColumn);
}
