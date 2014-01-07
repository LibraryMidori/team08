package com.example.treasurehunt;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Type.CubemapFace;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

/*
 * @author group 8
 */

public class Game extends Activity {

	/*
	 * Properties
	 */
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
		level++;
		totalTraps = minTraps + level;

		timer = maxTime;
		numberOfRows = 16;
		numberOfColumns = 30;
		trapsRemain = totalTraps;

		cellWidth = 24;
		cellPadding = 2;

		// Tracking time
		clock = new Handler();

		isGameOver = false;
		isTrapHere = false;
		isGameStart = false;
		isMapGen = false;
	}

	private void startNewGame() {
		createMap();
		showMap();
	}

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
							rippleUncover(currentRow, currentColumn);
							
//							if(cells[currentRow][currentColumn].hasTrap()) {
//								finishGame(currentRow, currentColumn);
//							}
//							
//							if(checkGameWin()) {
//								winGame();
//							}
						}
					}
				});
				
				
				
			}
		}
	}

	private void showMap() {

	}

	private void genMap(int rowClicked, int columnClicked) {

	}

	private void rippleUncover(int rowClicked, int columnClicked) {
		if (cells[rowClicked][columnClicked].hasTrap()
				|| cells[rowClicked][columnClicked].hasTreasure()
				|| cells[rowClicked][columnClicked].isFlagged()) {

		}
	}
	
	private void startTimer() {
		clock.removeCallbacks(updateTimeElasped);
	}

	private void stopTimer() {

	}

	/*
	 * 
	 */
	private Runnable updateTimeElasped = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long currentMilliseconds = System.currentTimeMillis();
			++timer;

			// add notification
			clock.postAtTime(this, currentMilliseconds);
			// notify to call back after 1 seconds
			// basically to remain in the timer loop
			clock.postDelayed(updateTimeElasped, 1000);
		}
	};

}
