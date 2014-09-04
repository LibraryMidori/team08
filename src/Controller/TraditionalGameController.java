package Controller;

import GameRule.SingletonSetupData;
import MapHolder.GameData;
import MapHolder.IMap;
import MapHolder.MapTradition;
import PrivateData.PrivateDataQuizGame;

public class TraditionalGameController implements IGameController {

	public TraditionalGameController() {

	}

	public void onClickOnCellHandle(int currentRow, int currentColumn) {

	}

	public boolean onLongClickOnCellHandle(int currentRow, int currentColumn) {
		return false;
	}

	public void flagAndDoubtHandle(int currentRow, int currentColumn) {

	}

	public IMap gameController(int _level, PrivateDataQuizGame quizData) {
		int maxTime = SingletonSetupData.getInstance().getMaxTime();
		int minTraps = SingletonSetupData.getInstance().getMinTraps();

		switch (_level) {
		case 1:
			return setUpGame(maxTime, minTraps, quizData);
		case 2:
			return setUpGame(maxTime, minTraps + _level, quizData);
		case 3:
			return setUpGame(maxTime, minTraps + _level, quizData);
		case 4:
			return setUpGame(maxTime - 60, minTraps + _level, quizData);
		case 5:
			return setUpGame(maxTime - 60, minTraps + _level, quizData);
		case 6:
			return setUpGame(maxTime - 60, minTraps + _level, quizData);
		case 7:
			return setUpGame(maxTime - 60 * 2, minTraps + _level, quizData);
		case 8:
			return setUpGame(maxTime - 60 * 2, minTraps + _level, quizData);
		case 9:
			return setUpGame(maxTime - 60 * 2, minTraps + _level, quizData);
		case 10:
			return setUpGame(maxTime - 60 * 3, minTraps + _level, quizData);
		case 11:
			return setUpGame(maxTime - 60 * 3, minTraps + _level, quizData);
		case 12:
			return setUpGame(maxTime - 60 * 3, minTraps + _level, quizData);
		case 13:
			return setUpGame(maxTime - 60 * 4, minTraps + _level, quizData);
		case 14:
			return setUpGame(maxTime - 60 * 4, minTraps + _level, quizData);
		case 15:
			return setUpGame(maxTime - 60 * 4, minTraps + _level, quizData);
		default:
			return setUpGame(maxTime, minTraps, quizData);
		}

	}

	private IMap setUpGame(int playTime, int numberOfTraps,
			PrivateDataQuizGame quizData) {
		int numberOfRows = quizData.getNumberOfRows();
		int numberOfColumns = quizData.getNumberOfColumns();

		GameData.getInstance().setTrapsRemain(numberOfTraps);
		return new MapTradition(numberOfRows, numberOfColumns, numberOfTraps);
	}

}
