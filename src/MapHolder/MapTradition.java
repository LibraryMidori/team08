package MapHolder;

import java.util.Random;

import CellPackage.Cell;
import android.content.Context;
import android.util.Log;

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
			}
		}
	}

	public boolean checkGameWin(int currentRow, int currentCol) {
		return cells[currentRow][currentCol].hasTreasure();
	}

	public Cell getCellByIndex(int row, int col) {
		return cells[row][col];
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
		if (cells[rowClicked][columnClicked].hasTrap()
				|| cells[rowClicked][columnClicked].isFlagged()
				|| cells[rowClicked][columnClicked].isDoubted()) {
			return;
		}

		if (!cells[rowClicked][columnClicked].isClickable()) {
			return;
		}

		cells[rowClicked][columnClicked].OpenCell();
		if (cells[rowClicked][columnClicked].getNumberOfTrapsInSurrounding() != 0) {
			return;
		}

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				// check all the above checked conditions
				// if met then open subsequent blocks
				if (cells[rowClicked + row - 1][columnClicked + column - 1]
						.isCovered()
						&& (rowClicked + row - 1 > 0)
						&& (columnClicked + column - 1 > 0)
						&& (rowClicked + row - 1 < numberOfRows + 1)
						&& (columnClicked + column - 1 < numberOfCols + 1)) {
					rippleUncover(rowClicked + row - 1, columnClicked + column
							- 1);
				}
			}
		}

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
