package MapHolder;

import java.util.Random;

import CellPackage.Cell;
import Timer.Timer;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class MapTradition implements IMap {
	private Cell cells[][];
	private int numberOfRows;
	private int numberOfCols;
	private int totalTraps;

	public MapTradition(int numberOfRows, int numberOfCols, int totalTraps) {
		cells = new Cell[numberOfRows + 2][numberOfCols + 2];
		this.numberOfCols = numberOfCols;
		this.numberOfRows = numberOfRows;
	}

	@Override
	public void createMap(Context context) {
		for (int row = 0; row < numberOfRows + 2; row++) {
			for (int column = 0; column < numberOfCols + 2; column++) {
				cells[row][column] = new Cell(context);
				// cells[row][column].setDefaults();

				final int currentRow = row;
				final int currentColumn = column;

				cells[row][column].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						onClickOnCellHandle(currentRow, currentColumn);
					}
				});

				// add Long Click listener
				// this is treated as right mouse click listener
				cells[row][column]
						.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View view) {
								// simulate a left-right (middle) click
								// if it is a long click on an opened trap then
								// open all surrounding blocks
								return onLongClickOnCellHandle(currentRow,
										currentColumn);
							}
						});
			}
		}

	}

	private void onClickOnCellHandle(int currentRow, int currentCol) {
		if (!GameData.getInstance().isGameStart()) {
			Timer.getInstance().startTimer();
			GameData.getInstance().setGameStart(true);
		}

		if (!GameData.getInstance().isMapGen()) {
			genMap(currentRow, currentCol);
			GameData.getInstance().setMapGen(true);
		}

		if (!cells[currentRow][currentCol].isFlagged()) {
			rippleUncover(currentRow, currentCol);

			if (cells[currentRow][currentCol].hasTrap()) {
				GameData.getInstance().setLives(
						GameData.getInstance().getLives() - 1);
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() - 1);
				cells[currentRow][currentCol].OpenCell();
				cells[currentRow][currentCol].setFlag(true);
			}

			if (checkGameWin(currentRow, currentCol)) {
				GameData.getInstance().setWinGame(true);
			}
		}
	}

	public boolean checkGameWin(int currentRow, int currentCol) {
		return cells[currentRow][currentCol].hasTreasure();
	}

	public Cell getCellByIndex(int row, int col) {
		return cells[row][col];
	}

	private boolean onLongClickOnCellHandle(int currentRow, int currentCol) {

		if (!cells[currentRow][currentCol].isCovered()
				&& (cells[currentRow][currentCol]
						.getNumberOfTrapsInSurrounding() > 0)
				&& !GameData.getInstance().isGameOver()) {
			int nearbyFlaggedBlocks = 0;
			for (int previousRow = -1; previousRow < 2; previousRow++) {
				for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
					if (cells[currentRow + previousRow][currentCol
							+ previousColumn].isFlagged()) {
						nearbyFlaggedBlocks++;
					}
				}
			}

			// if flagged block count is equal to nearby trap count then open
			// nearby blocks
			if (nearbyFlaggedBlocks == cells[currentRow][currentCol]
					.getNumberOfTrapsInSurrounding()) {
				for (int previousRow = -1; previousRow < 2; previousRow++) {
					for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
						// don't open flagged blocks
						if (!cells[currentRow + previousRow][currentCol
								+ previousColumn].isFlagged()) {
							// open blocks till we get
							// numbered block
							rippleUncover(currentRow + previousRow, currentCol
									+ previousColumn);

							// did we clicked a trap
							if (cells[currentRow + previousRow][currentCol
									+ previousColumn].hasTrap()) {

								cells[currentRow + previousRow][currentCol
										+ previousColumn].OpenCell();
								GameData.getInstance().setLives(
										GameData.getInstance().getLives() - 1);
								// livesText.setText("" + lives);
								GameData.getInstance()
										.setTrapsRemain(
												GameData.getInstance()
														.getTrapsRemain() - 1);
								// trapText.setText("" + trapsRemain);
								if (GameData.getInstance().getLives() <= 0) {
									finishGame(0, 0);
								}

							}
						}
					}
				}
			}
			return true;
		}

		// if clicked cells is enabled, clickable or flagged

		flagAndDoubtHandle(currentRow, currentCol);
		return true;
	}

	private void flagAndDoubtHandle(int currentRow, int currentColumn) {
		// we got 3 situations
		// 1. empty blocks to flagged
		// 2. flagged to question mark
		// 3. question mark to blank

		if (cells[currentRow][currentColumn].isClickable()
				&& (cells[currentRow][currentColumn].isEnabled() || cells[currentRow][currentColumn]
						.isFlagged())) {
			// case 1. set blank block to flagged
			if (!cells[currentRow][currentColumn].isFlagged()
					&& !cells[currentRow][currentColumn].isDoubted()) {
				cells[currentRow][currentColumn].setFlagIcon(true);
				cells[currentRow][currentColumn].setFlag(true);
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() - 1); // reduce
																		// trap
																		// count
			}
			// case 2. set flagged to question mark
			else if (!cells[currentRow][currentColumn].isDoubted()) {
				cells[currentRow][currentColumn].setDoubt(true);
				cells[currentRow][currentColumn].setFlagIcon(false);
				cells[currentRow][currentColumn].setDoubtIcon(true);
				cells[currentRow][currentColumn].setFlag(false);
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() + 1); // increase
																		// trap
																		// count
			}
			// case 3. change to blank square
			else {
				cells[currentRow][currentColumn].clearAllIcons();
				cells[currentRow][currentColumn].setDoubt(false);
				// if it is flagged then increment trap count
				if (cells[currentRow][currentColumn].isFlagged()) {
					GameData.getInstance().setTrapsRemain(
							GameData.getInstance().getTrapsRemain() + 1); // increase
																			// trap
																			// count
				}
				// remove flagged status
				cells[currentRow][currentColumn].setFlag(false);
			}

		}
	}

	@Override
	public void genMap(int currentRow, int currentCol) {
		genTreasure(currentRow, currentCol);
		genTraps(currentRow, currentCol);
		setTheNumberOfSurroundingTrap();
	}

	private void genTreasure(int currentRow, int currentCol) {
		Random rand = new Random();
		int treasureRow = 0, treasureCol = 0;

		// Set the treasure position
		treasureRow = rand.nextInt(numberOfRows - 1) + 2;
		if (treasureRow >= numberOfRows) {
			treasureRow = numberOfRows - 2;
		}

		treasureCol = rand.nextInt(numberOfCols - 1) + 2;
		if (treasureCol >= numberOfCols) {
			treasureCol = numberOfCols - 2;
		}

		// For debugging
		Log.e("8A >>>>", "Treasure: " + treasureRow + " " + treasureCol);

		// Make sure the treasure is not near the clicked cell
		if (!isTreasureNear(treasureRow, treasureCol, currentRow, currentCol)) {
			if (treasureRow < numberOfRows / 2) {
				treasureRow = (numberOfRows - treasureRow) / 2;
				treasureCol = (numberOfCols - treasureCol) / 2;
			} else {
				treasureRow = (treasureRow) / 2;
				treasureCol = (treasureCol) / 2;
			}
		}

		for (int previousRow = -1; previousRow < 2; previousRow++) {
			for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
				if ((previousRow != 0) || (previousColumn != 0)) {
					cells[treasureRow + previousRow][treasureCol
							+ previousColumn].setTrap();
				} else {
					cells[treasureRow + previousRow][treasureCol
							+ previousColumn].setTreasure();
				}
			}
		}
	}

	private boolean isTreasureNear(int rowCheck, int columnCheck,
			int rowClicked, int columnClicked) {
		if (((rowCheck != columnClicked) || (columnCheck - 1 != rowClicked))
				&& ((rowCheck != columnClicked) || (columnCheck != rowClicked))
				&& ((rowCheck != columnClicked) || (columnCheck + 1 != rowClicked))
				&& ((rowCheck != columnClicked) || (columnCheck + 2 != rowClicked))
				&& ((rowCheck != columnClicked) || (columnCheck + 3 != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck - 1 != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck + 1 != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck + 2 != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck + 3 != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck - 1 != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck + 1 != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck + 2 != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck + 3 != rowClicked))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void genTraps(int rowClicked, int columnClicked) {
		Random rand = new Random();
		int trapRow, trapColumn;
		// set traps excluding the location where user clicked
		for (int row = 0; row < totalTraps - 8; row++) {
			trapRow = rand.nextInt(numberOfCols);
			trapColumn = rand.nextInt(numberOfRows);

			// set the 8 surrounded cells of the clicked cell have no trap
			if (isNearTheClickedCell(trapRow, trapColumn, rowClicked,
					columnClicked)) {
				if (cells[trapColumn + 1][trapRow + 1].hasTrap()
						|| cells[trapColumn + 1][trapRow + 1].hasTreasure()) {
					row--; // trap is already there, don't repeat for same block
				}
				// plant trap at this location
				cells[trapColumn + 1][trapRow + 1].setTrap();
			}
			// exclude the user clicked location
			else {
				row--;
			}
		}
	}

	@Override
	public void setTheNumberOfSurroundingTrap() {
		int nearByTrapCount;
		// count number of traps in surrounding blocks
		for (int row = 0; row < numberOfRows + 1; row++) {
			for (int column = 0; column < numberOfCols + 1; column++) {
				// for each block find nearby trap count
				nearByTrapCount = 0;
				if ((row != 0) && (row != (numberOfRows + 1)) && (column != 0)
						&& (column != (numberOfRows + 1))) {
					// check in all nearby blocks
					for (int previousRow = -1; previousRow < 2; previousRow++) {
						for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
							Log.e("8C>>>>>>>>>>>", "row " + row + previousRow
									+ " - col " + column + previousColumn);
							if (cells[row + previousRow][column
									+ previousColumn].hasTrap()) {
								// a trap was found so increment the counter
								nearByTrapCount++;
							}
						}
					}
					cells[row][column]
							.setNumberOfTrapsInSurrounding(nearByTrapCount);
				}
				// for side rows (0th and last row/column)
				// set count as 9 and mark it as opened
				else {
					cells[row][column].setNumberOfTrapsInSurrounding(9);
					cells[row][column].OpenCell();
				}

			}
		}
	}

	@Override
	public void rippleUncover(int rowClicked, int columnClicked) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isNearTheClickedCell(int rowCheck, int columnCheck,
			int rowClicked, int columnClicked) {
		if (((rowCheck != columnClicked) || (columnCheck != rowClicked))
				&& ((rowCheck != columnClicked) || (columnCheck + 1 != rowClicked))
				&& ((rowCheck != columnClicked) || (columnCheck + 2 != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck + 1 != rowClicked))
				&& ((rowCheck + 1 != columnClicked) || (columnCheck + 2 != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck + 1 != rowClicked))
				&& ((rowCheck + 2 != columnClicked) || (columnCheck + 2 != rowClicked))) {
			return true;
		} else {
			return false;
		}
	}

	public void finishGame(int currentRow, int currentCol) {
		// show all traps
		// disable all traps
		for (int row = 1; row < numberOfRows + 1; row++) {
			for (int column = 1; column < numberOfCols + 1; column++) {
				// disable block
				// cells[row][column].setCellAsDisabled(false);
				cells[row][column].disableCell();

				// block has trap and is not flagged
				if (cells[row][column].hasTrap()
						&& !cells[row][column].isFlagged()) {
					// set trap icon
					cells[row][column].setTrapIcon(false);
				}

				// block is flagged and doesn't not have trap
				if (!cells[row][column].hasTrap()
						&& cells[row][column].isFlagged()) {
					// set flag icon
					cells[row][column].setFlagIcon(false);
				}

				// block is flagged
				if (cells[row][column].isFlagged()) {
					// disable the block
					cells[row][column].setClickable(false);
				}

				// set treasure icon
				if (cells[row][column].hasTreasure()) {
					// set treasure icon
					cells[row][column].setTreasureIcon(false);
				}
			}
		}

		// trigger trap
		cells[currentRow][currentCol].triggerTrap();
	}

	public void winGame() {
		for (int row = 1; row < numberOfRows + 1; row++) {
			for (int column = 1; column < numberOfCols + 1; column++) {
				cells[row][column].setClickable(false);
				if (cells[row][column].hasTrap()) {
					cells[row][column].setTrapIcon(true);
				}
				if (cells[row][column].hasTreasure())
					cells[row][column].setTreasureIcon(true);
			}
		}
	}
}
