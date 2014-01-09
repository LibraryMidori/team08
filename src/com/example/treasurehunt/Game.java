package com.example.treasurehunt;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

/*
 * @author group 8
 */

public class Game extends Activity {

	/*
	 * Properties
	 */
	private TableLayout map;
	private Cell cells[][];
	private int numberOfRows = 0;
	private int numberOfColumns = 0;
	private int totalTraps = 0;
	private int trapsRemain = 0;
	private static int level = 0;
	private int lives = 0;
	private int totalScore = 0;

	private final int minTraps = 84;
	private final int maxTime = 480;

	private int cellWidth;
	private int cellPadding;

	// Tracking time
	private Handler clock;
	private int timer;

	private boolean isGameOver;
	private boolean isTrapHere;
	private boolean isGameStart;
	private boolean isMapGen;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_root);
		Options option = new Options();
		option.inSampleSize = 2;
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.gamebg, option);
		if (bmp != null) {
			layout.setBackgroundDrawable(new BitmapDrawable(getResources(), bmp));
		}

		// @author 8C Pham Duy Hung
		initView();
		startNewGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	/*
	 * Initial view
	 * 
	 * @author 8C Pham Duy Hung
	 */
	private void initView() {
		map = (TableLayout) findViewById(R.id.Map);

		level++;
		gameController(level);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	/*
	 * This method control the level ingame
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param level the current level
	 */
	private void gameController(int level) {
		totalTraps = minTraps + level;
		switch (level) {
		case 1:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0;

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 2:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 3:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 4:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 5:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 6:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 7:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 8:
			timer = maxTime;
			numberOfRows = 16;
			numberOfColumns = 30;

			trapsRemain = totalTraps;
			totalScore = 0; // reset after

			cellWidth = 27;
			cellPadding = 2;

			// Tracking time
			clock = new Handler();

			isGameOver = false;
			isTrapHere = false;
			isGameStart = false;
			isMapGen = false;
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		default:
			break;
		}
	}

	/*
	 * Start the game
	 * 
	 * @author 8C Pham Duy Hung
	 */
	private void startNewGame() {
		// TODO: generate the initial information

		createMap();
		showMap();

		isGameOver = false;
		isTrapHere = false;
		isGameStart = false;
	}

	/*
	 * Create new map
	 * 
	 * @author 8C Pham Duy Hung
	 */
	private void createMap() {

		// We make more 2 row and column, the 0 row/column and the last one are
		// not showed
		cells = new Cell[numberOfRows + 2][numberOfColumns + 2];

		for (int row = 0; row < numberOfRows + 2; row++) {
			for (int column = 0; column < numberOfColumns + 2; column++) {
				cells[row][column] = new Cell(this);
				cells[row][column].setDefaults();

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

	/*
	 * This method handles the on click event
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param currentRow the position of the clicked cell
	 * 
	 * @param currentCol the position of the clicked cell
	 */
	private void onClickOnCellHandle(int currentRow, int currentColumn) {

		if (!isGameStart) {
			startTimer();
			isGameStart = true;
		}

		if (!isMapGen) {
			genMap(currentRow, currentColumn);
			isMapGen = true;
		}

		if (!cells[currentRow][currentColumn].isFlagged()) {
			rippleUncover(currentRow, currentColumn);

			if (cells[currentRow][currentColumn].hasTrap()) {
				finishGame(currentRow, currentColumn);
			}

			if (checkGameWin(cells[currentRow][currentColumn])) {
				// winGame();
			}
		}
	}

	/*
	 * This method handles the on long click event
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param currentRow the position of the clicked cell
	 * 
	 * @param currentCol the position of the clicked cell
	 */
	private boolean onLongClickOnCellHandle(int currentRow, int currentColumn) {

		if (!cells[currentRow][currentColumn].isCovered()
				&& (cells[currentRow][currentColumn]
						.getNumberOfTrapsInSurrounding() > 0) && !isGameOver) {
			int nearbyFlaggedBlocks = 0;
			for (int previousRow = -1; previousRow < 2; previousRow++) {
				for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
					if (cells[currentRow + previousRow][currentColumn
							+ previousColumn].isFlagged()) {
						nearbyFlaggedBlocks++;
					}
				}
			}

			// if flagged block count is equal to nearby trap count then open
			// nearby blocks
			if (nearbyFlaggedBlocks == cells[currentRow][currentColumn]
					.getNumberOfTrapsInSurrounding()) {
				for (int previousRow = -1; previousRow < 2; previousRow++) {
					for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
						// don't open flagged blocks
						if (!cells[currentRow + previousRow][currentColumn
								+ previousColumn].isFlagged()) {
							// open blocks till we get
							// numbered block
							rippleUncover(currentRow + previousRow,
									currentColumn + previousColumn);

							// did we clicked a trap
							if (cells[currentRow + previousRow][currentColumn
									+ previousColumn].hasTrap()) {
								finishGame(currentRow + previousRow,
										currentColumn + previousColumn);
							}
						}
					}
				}
			}
			return true;
		}

		// if clicked cells is enabled, clickable or flagged

		flagAndDoubtHandle(currentRow, currentColumn);
		return true;
	}

	/*
	 * This method handles the flag and doubt situations
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param currentRow the position of the clicked cell
	 * 
	 * @param currentCol the position of the clicked cell
	 */
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
				trapsRemain--; // reduce trap count
			}
			// case 2. set flagged to question mark
			else if (!cells[currentRow][currentColumn].isDoubted()) {
				cells[currentRow][currentColumn].setDoubt(true);
				cells[currentRow][currentColumn].setFlagIcon(false);
				cells[currentRow][currentColumn].setDoubtIcon(true);
				cells[currentRow][currentColumn].setFlag(false);
				trapsRemain++; // increase trap count
			}
			// case 3. change to blank square
			else {
				cells[currentRow][currentColumn].clearAllIcons();
				cells[currentRow][currentColumn].setDoubt(false);
				// if it is flagged then increment trap count
				if (cells[currentRow][currentColumn].isFlagged()) {
					trapsRemain++; // increase trap count
				}
				// remove flagged status
				cells[currentRow][currentColumn].setFlag(false);
			}

		}
	}

	/*
	 * Show map procedure
	 * 
	 * @author 8C Pham Duy Hung
	 */
	private void showMap() {
		// remember we will not show 0th and last Row and Columns
		// they are used for calculation purposes only
		for (int row = 1; row < numberOfRows + 1; row++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams(
					(cellWidth + 2 * cellPadding) * numberOfColumns, cellWidth
							+ 2 * cellPadding));

			for (int column = 1; column < numberOfColumns + 1; column++) {
				cells[row][column].setLayoutParams(new LayoutParams(cellWidth
						+ 2 * cellPadding, cellWidth + 2 * cellPadding));
				cells[row][column].setPadding(cellPadding, cellPadding,
						cellPadding, cellPadding);
				tableRow.addView(cells[row][column]);
			}
			map.addView(tableRow, new TableLayout.LayoutParams(
					(cellWidth + 2 * cellPadding) * numberOfColumns, cellWidth
							+ 2 * cellPadding));
		}
	}

	/*
	 * Generate the map
	 * 
	 * @author 8A Tran Trong Viet
	 * 
	 * @param rowClicked the position of clicked cell
	 * 
	 * @param columnClicked the position of clicked cell
	 */
	private void genMap(int rowClicked, int columnClicked) {

		genTreasure(rowClicked, columnClicked);
		genTraps(rowClicked, columnClicked);
		setTheNumberOfSurroundingTrap();
	}

	/*
	 * Generate the treasure position in map
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param rowClicked the position of the first-clicked-cell
	 * 
	 * @param columnClicked the position of the first-clicked-cell
	 */
	private void genTreasure(int rowClicked, int columnClicked) {
		Random rand = new Random();
		int treasureRow = 0, treasureCol = 0;

		// Set the treasure position
		treasureRow = rand.nextInt(numberOfRows - 1) + 2;
		if (treasureRow >= numberOfRows) {
			treasureRow = numberOfRows - 2;
		}

		treasureCol = rand.nextInt(numberOfColumns - 1) + 2;
		if (treasureCol >= numberOfColumns) {
			treasureCol = numberOfColumns - 2;
		}

		// Make sure the treasure is not near the clicked cell
		if (isNearTheClickedCell(treasureRow, treasureCol, rowClicked,
				columnClicked)) {
			if (treasureRow < numberOfRows / 2) {
				treasureRow = (numberOfRows - treasureRow) / 2;
				treasureCol = (numberOfColumns - treasureCol) / 2;
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

	/*
	 * Generate the traps position in map
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param rowClicked the position of the first-clicked-cell
	 * 
	 * @param columnClicked the position of the first-clicked-cell
	 */
	private void genTraps(int rowClicked, int columnClicked) {
		Random rand = new Random();
		int trapRow, trapColumn;
		// set traps excluding the location where user clicked
		for (int row = 0; row < totalTraps - 8; row++) {
			trapRow = rand.nextInt(numberOfColumns);
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

	/*
	 * Set the number of surrounding trap
	 * 
	 * @author 8C Pham Duy Hung
	 */
	private void setTheNumberOfSurroundingTrap() {

		int nearByTrapCount;
		// count number of traps in surrounding blocks
		for (int row = 0; row < numberOfRows + 2; row++) {
			for (int column = 0; column < numberOfColumns + 2; column++) {
				// for each block find nearby trap count
				nearByTrapCount = 0;
				if ((row != 0) && (row != (numberOfRows + 1)) && (column != 0)
						&& (column != (numberOfColumns + 1))) {
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

	/*
	 * Check if this cell is near the clicked cell
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param rowCheck the row which want to check
	 * 
	 * @param columnCheck the column which want to check
	 * 
	 * @param rowClicked the row of the clicked cell
	 * 
	 * @param columnClick the column of the clicked cell
	 */
	private boolean isNearTheClickedCell(int rowCheck, int columnCheck,
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

	/*
	 * Open the cells which surrounded the no-trap-surrounded cell continuously
	 * 
	 * @author 8-B Pham Hung Cuong
	 * 
	 * @param rowClicked the row of the clicked position
	 * 
	 * @param columnClicked the column of the clicked position
	 */
	private void rippleUncover(int rowClicked, int columnClicked) {
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
						&& (columnClicked + column - 1 < numberOfColumns + 1)) {
					rippleUncover(rowClicked + row - 1, columnClicked + column
							- 1);
				}
			}
		}
	}

	/*
	 * Check the game wins or not (should do this for changing rules)
	 * 
	 * @author 8C Pham Duy Hung
	 * 
	 * @param cell the cell which is clicked
	 */
	private boolean checkGameWin(Cell cell) {
		return cell.hasTreasure();
	}

	// private void winGame() {
	// stopTimer();
	// isTimerStarted = false;
	// isGameOver = true;
	// minesToFind = 0; // set mine count to 0
	//
	// // set icon to cool dude
	// btnSmile.setBackgroundResource(R.drawable.cool);
	//
	// updateMineCountDisplay(); // update mine count
	//
	// // disable all buttons
	// // set flagged all un-flagged blocks
	// for (int row = 1; row < numberOfRowsInMineField + 1; row++) {
	// for (int column = 1; column < numberOfColumnsInMineField + 1; column++) {
	// blocks[row][column].setClickable(false);
	// if (blocks[row][column].hasMine()) {
	// blocks[row][column].setBlockAsDisabled(false);
	// blocks[row][column].setFlagIcon(true);
	// }
	// }
	// }
	//
	// // show message
	// showDialog("You won in " + Integer.toString(secondsPassed)
	// + " seconds!", 1000, false, true);
	// }

	/*
	 * Finish the game
	 * 
	 * @author 8B Pham Hung Cuong
	 */
	private void finishGame(int currentRow, int currentColumn) {
		isGameOver = true; // mark game as over
		stopTimer(); // stop timer
		isGameStart = false;

		// show all traps
		// disable all traps
		for (int row = 1; row < numberOfRows + 1; row++) {
			for (int column = 1; column < numberOfColumns + 1; column++) {
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
					// set trap icon
					cells[row][column].setTreasureIcon(false);
				}
			}
		}

		// trigger trap
		cells[currentRow][currentColumn].triggerTrap();
	}

	/*
	 * Start time time
	 * 
	 * @author: 8B Pham Hung Cuong
	 */
	public void startTimer() {
		clock.removeCallbacks(updateTimeElasped);
		// delay clock for a second
		clock.postDelayed(updateTimeElasped, 1000);
	}

	/*
	 * Stop the time
	 * 
	 * @author 8B Pham Hung Cuong
	 */

	public void stopTimer() {
		// disable call backs
		clock.removeCallbacks(updateTimeElasped);
	}

	/*
	 * This properties must be set up for handling the clock
	 * 
	 * @author 8B Pham Hung Cuong
	 */
	private Runnable updateTimeElasped = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long currentMilliseconds = System.currentTimeMillis();
			++timer;

			// TODO: set the time to the screen here

			// add notification
			clock.postAtTime(this, currentMilliseconds);
			// notify to call back after 1 seconds
			// basically to remain in the timer loop
			clock.postDelayed(updateTimeElasped, 1000);
		}
	};

}
