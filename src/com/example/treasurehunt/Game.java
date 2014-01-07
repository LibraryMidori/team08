package com.example.treasurehunt;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
	private int level = 0;

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
		totalTraps = minTraps + level;

		timer = maxTime;
		numberOfRows = 16;
		numberOfColumns = 30;
		trapsRemain = totalTraps;

		cellWidth = 27;
		cellPadding = 2;

		// Tracking time
		clock = new Handler();

		isGameOver = false;
		isTrapHere = false;
		isGameStart = false;
		isMapGen = false;
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
	}

	/*
	 * Create new map
	 * 
	 * @author 8C Pham Duy Hung
	 */
	private void createMap() {

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
						if (!isGameStart) {
							startTimer();
							isGameStart = true;
						}

						if (!isMapGen) {
							genMap(currentRow, currentColumn);
							isMapGen = true;
						}

						if (!cells[currentRow][currentColumn].isFlagged()) {
							// rippleUncover(currentRow, currentColumn);

							// if(cells[currentRow][currentColumn].hasTrap()) {
							// finishGame(currentRow, currentColumn);
							// }
							//
							// if(checkGameWin()) {
							// winGame();
							// }
						}
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
								if (!cells[currentRow][currentColumn]
										.isCovered()
										&& (cells[currentRow][currentColumn]
												.getNumberOfTrapsInSorrounding() > 0)
										&& !isGameOver) {
									int nearbyFlaggedBlocks = 0;
									for (int previousRow = -1; previousRow < 2; previousRow++) {
										for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
											if (cells[currentRow + previousRow][currentColumn
													+ previousColumn]
													.isFlagged()) {
												nearbyFlaggedBlocks++;
											}
										}
									}

									// if flagged block count is equal to nearby
									// trap count
									// then open nearby blocks
									if (nearbyFlaggedBlocks == cells[currentRow][currentColumn]
											.getNumberOfTrapsInSorrounding()) {
										for (int previousRow = -1; previousRow < 2; previousRow++) {
											for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
												// don't open flagged blocks
												if (!cells[currentRow
														+ previousRow][currentColumn
														+ previousColumn]
														.isFlagged()) {
													// open blocks till we get
													// numbered block
													rippleUncover(
															currentRow
																	+ previousRow,
															currentColumn
																	+ previousColumn);

													// did we clicked a trap
													if (cells[currentRow
															+ previousRow][currentColumn
															+ previousColumn]
															.hasTrap()) {
														// oops game over
														finishGame(
																currentRow
																		+ previousRow,
																currentColumn
																		+ previousColumn);
													}

													// did we win the game
													/*
													 * if (checkGameWin()) { //
													 * mark game as win
													 * winGame(); }
													 */
												}
											}
										}
									}

									// as we no longer want to judge this
									// gesture so return
									// not returning from here will actually
									// trigger other action
									// which can be marking as a flag or
									// question mark or blank
									return true;
								}

								// if clicked block is enabled, clickable or
								// flagged
								if (cells[currentRow][currentColumn]
										.isClickable()
										&& (cells[currentRow][currentColumn]
												.isEnabled() || cells[currentRow][currentColumn]
												.isFlagged())) {

									// for long clicks set:
									// 1. empty blocks to flagged
									// 2. flagged to question mark
									// 3. question mark to blank

									// case 1. set blank block to flagged
									if (!cells[currentRow][currentColumn]
											.isFlagged()
											&& !cells[currentRow][currentColumn]
													.isDoubted()) {
										cells[currentRow][currentColumn]
												.setCellAsDisabled(false);
										cells[currentRow][currentColumn]
												.setFlagIcon(true);
										cells[currentRow][currentColumn]
												.setFlag(true);
										trapsRemain--; // reduce trap count
									}
									// case 2. set flagged to question mark
									else if (!cells[currentRow][currentColumn]
											.isDoubted()) {
										cells[currentRow][currentColumn]
												.setCellAsDisabled(true);
										cells[currentRow][currentColumn]
												.setDoubt(true);
										cells[currentRow][currentColumn]
												.setFlag(false);
										cells[currentRow][currentColumn]
												.setDoubt(true);
										trapsRemain++; // increase trap count
									}
									// case 3. change to blank square
									else {
										cells[currentRow][currentColumn]
												.setCellAsDisabled(true);
										cells[currentRow][currentColumn]
												.clearAllIcons();
										cells[currentRow][currentColumn]
												.setDoubt(false);
										// if it is flagged then increment trap
										// count
										if (cells[currentRow][currentColumn]
												.isFlagged()) {
											trapsRemain++; // increase trap
															// count
										}
										// remove flagged status
										cells[currentRow][currentColumn]
												.setFlag(false);
									}

								}

								return true;
							}
						});
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
	 * 
	 * @author 8A Tran Trong Viet
	 * 
	 * @param rowClicked
	 * 
	 * @param columnClicked
	 */
	private void genMap(int rowClicked, int columnClicked) {
		Random rand = new Random();
		int trapRow, trapColumn;

		// Modify: set treasure
		// @author 8A Tran Trong Viet
		int treasureRow = rand.nextInt(numberOfRows - 1) + 2;
		if (treasureRow == numberOfRows) {
			treasureRow--;
		}

		int treasureCol = rand.nextInt(numberOfColumns - 1) + 2;
		if (treasureCol == numberOfColumns) {
			treasureCol--;
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

		// End modify

		// set traps excluding the location where user clicked
		for (int row = 0; row < totalTraps - 8; row++) {
			trapRow = rand.nextInt(numberOfColumns);
			trapColumn = rand.nextInt(numberOfRows);
			if (((trapRow != columnClicked) || (trapColumn != rowClicked))
					&& ((trapRow != columnClicked) || (trapColumn + 1 != rowClicked))
					&& ((trapRow != columnClicked) || (trapColumn + 2 != rowClicked))
					&& ((trapRow + 1 != columnClicked) || (trapColumn != rowClicked))
					&& ((trapRow + 1 != columnClicked) || (trapColumn + 1 != rowClicked))
					&& ((trapRow + 1 != columnClicked) || (trapColumn + 2 != rowClicked))
					&& ((trapRow + 2 != columnClicked) || (trapColumn != rowClicked))
					&& ((trapRow + 2 != columnClicked) || (trapColumn + 1 != rowClicked))
					&& ((trapRow + 2 != columnClicked) || (trapColumn + 2 != rowClicked))) {
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
							.setNumberOfSurroundingTraps(nearByTrapCount);
				}
				// for side rows (0th and last row/column)
				// set count as 9 and mark it as opened
				else {
					cells[row][column].setNumberOfSurroundingTraps(9);
					cells[row][column].OpenCell();
				}
			}
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
				|| cells[rowClicked][columnClicked].hasTreasure()
				|| cells[rowClicked][columnClicked].isFlagged()) {
			return;
		}
		cells[rowClicked][columnClicked].OpenCell();
		if (cells[rowClicked][columnClicked].getNumberOfTrapsInSorrounding() != 0) {
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
	 * Finish the game
	 * 
	 * @author 8B Pham Hung Cuong
	 */
	private void finishGame(int currentRow, int currentColumn) {
		isGameOver = true; // mark game as over
		stopTimer(); // stop timer
		isGameStart = false;

		// show all traps
		// disable all blocks
		for (int row = 1; row < numberOfRows + 1; row++) {
			for (int column = 1; column < numberOfColumns + 1; column++) {
				// disable block
				cells[row][column].setCellAsDisabled(false);

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
	private void startTimer() {
		clock.removeCallbacks(updateTimeElasped);
		// delay clock for a second
		clock.postDelayed(updateTimeElasped, 1000);
	}

	private void stopTimer() {
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
