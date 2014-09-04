package com.example.treasurehunt;

import CellPackage.Cell;
import Controller.IGameController;
import MapHolder.GameData;
import MapHolder.IMap;
import PrivateData.PrivateDataQuizGame;
import Timer.Timer;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public abstract class AbstractGameView extends Activity implements IGameView {
	protected TableLayout map;
	protected Cell cells[][];
	protected PrivateDataQuizGame quizData;
	protected IGameController controller;

	protected IMap mapControl;

	// Sound
	protected MediaPlayer mp;

	// Pop up
	protected Handler handler;

	// Texts
	protected Typeface font, font2;
	protected TextView levelText, scoreText, timeText, livesText,
			finalScoreText, finalTimeText, trapText;

	// UI
	protected ImageView mImgViewResult;

	// Save Score
	protected SharedPreferences gamePrefs;

	protected void createdGameView() {
		gameController();
		initView();
		startNewGame();
	}

	protected abstract void gameController();

	protected abstract void initView();

	// protected abstract void createMap();

	// protected abstract void startNewGame();

	protected void startNewGame() {
		Timer.getInstance().setTimer(180);
		createMap();
		showMap();

		GameData.getInstance().setGameOver(false);
		GameData.getInstance().setGameStart(false);

		timeText.setText("" + Timer.getInstance().getTimer());
		Timer.getInstance().registed(this);

	}

	protected abstract void winGame();

	protected abstract void showMap();

	protected abstract void finishGame(int currentRow, int currentColumn);

	public void updateTime() {
		timeText.setText("" + Timer.getInstance().getTimer());
		if (Timer.getInstance().getTimer() == 0) {
			finishGame(0, 0);
		}
	}

	protected void setText() {
		levelText.setText("LEVEL " + GameData.getInstance().getLevel());
		scoreText.setText("" + GameData.getInstance().getTotalScore());
		livesText.setText("" + GameData.getInstance().getLives());
		trapText.setText("" + GameData.getInstance().getTrapsRemain());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		if (GameData.getInstance().getLives() > 0
				&& !GameData.getInstance().isGameFinish()) {
			saveGameState(GameData.getInstance().getLevel(), GameData
					.getInstance().getTotalScore(), GameData.getInstance()
					.getLives());
		}
		finish();
		super.onBackPressed();
	}

	private void saveGameState(int _level, int _score, int _lives) {
		SharedPreferences.Editor gameStateEdit = gamePrefs.edit();

		gameStateEdit.putString("saveGame", _level + " - " + _score + " - "
				+ _lives);
		gameStateEdit.commit();
	}

	protected void createMap() {
		int numberOfRows = quizData.getNumberOfRows();
		int numberOfColumns = quizData.getNumberOfColumns();

		mapControl.createMap(this);
		for (int row = 0; row < numberOfRows + 2; row++) {
			for (int col = 0; col < numberOfColumns + 2; col++) {
				final int currentRow = row;
				final int currentCol = col;

				mapControl.getCellByIndex(row, col).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								onClickOnCellHandle(currentRow, currentCol);
							}
						});

				mapControl.getCellByIndex(row, col).setOnLongClickListener(
						new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								return onLongClickOnCellHandle(currentRow,
										currentCol);
							}
						});
			}
		}
	}

	private void onClickOnCellHandle(int currentRow, int currentColumn) {

		if (!GameData.getInstance().isGameStart()) {
			Timer.getInstance().startTimer();
			GameData.getInstance().setGameStart(true);
		}

		if (!GameData.getInstance().isMapGen()) {
			mapControl.genMap(currentRow, currentColumn);
			GameData.getInstance().setMapGen(true);
		}

		if (!mapControl.getCellByIndex(currentRow, currentColumn).isFlagged()) {
			mapControl.rippleUncover(currentRow, currentColumn);

			if (mapControl.getCellByIndex(currentRow, currentColumn).hasTrap()) {
				GameData.getInstance().setLives(
						GameData.getInstance().getLives() - 1);
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() - 1);
				livesText.setText("" + GameData.getInstance().getLives());
				trapText.setText("" + GameData.getInstance().getTrapsRemain());
				mapControl.getCellByIndex(currentRow, currentColumn).OpenCell();
				mapControl.getCellByIndex(currentRow, currentColumn).setFlag(
						true);
				if (GameData.getInstance().getLives() <= 0) {
					finishGame(currentRow, currentColumn);
					livesText.setText("0");
				}
			}

			if (mapControl.checkGameWin(currentRow, currentColumn)) {
				winGame();
			}
		}
	}

	private boolean onLongClickOnCellHandle(int currentRow, int currentColumn) {

		if (!mapControl.getCellByIndex(currentRow, currentColumn).isCovered()
				&& (mapControl.getCellByIndex(currentRow, currentColumn)
						.getNumberOfTrapsInSurrounding() > 0)
				&& !GameData.getInstance().isGameOver()) {
			int nearbyFlaggedBlocks = 0;
			for (int previousRow = -1; previousRow < 2; previousRow++) {
				for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
					if (mapControl.getCellByIndex(currentRow + previousRow,
							currentColumn + previousColumn).isFlagged()) {
						nearbyFlaggedBlocks++;
					}
				}
			}

			// if flagged block count is equal to nearby trap count then open
			// nearby blocks
			if (nearbyFlaggedBlocks == mapControl.getCellByIndex(currentRow,
					currentColumn).getNumberOfTrapsInSurrounding()) {
				for (int previousRow = -1; previousRow < 2; previousRow++) {
					for (int previousColumn = -1; previousColumn < 2; previousColumn++) {
						// don't open flagged blocks
						if (!mapControl.getCellByIndex(
								currentRow + previousRow,
								currentColumn + previousColumn).isFlagged()) {
							// open blocks till we get
							// numbered block
							mapControl.rippleUncover(currentRow + previousRow,
									currentColumn + previousColumn);

							// did we clicked a trap
							if (mapControl.getCellByIndex(
									currentRow + previousRow,
									currentColumn + previousColumn).hasTrap()) {

								mapControl.getCellByIndex(
										currentRow + previousRow,
										currentColumn + previousColumn)
										.OpenCell();
								GameData.getInstance().setLives(
										GameData.getInstance().getLives() - 1);
								livesText.setText(""
										+ GameData.getInstance().getLives());
								GameData.getInstance()
										.setTrapsRemain(
												GameData.getInstance()
														.getTrapsRemain() - 1);
								trapText.setText(""
										+ GameData.getInstance()
												.getTrapsRemain());
								if (GameData.getInstance().getLives() <= 0) {
									livesText.setText("0");
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

		flagAndDoubtHandle(currentRow, currentColumn);
		return true;
	}

	private void flagAndDoubtHandle(int currentRow, int currentColumn) {
		// we got 3 situations
		// 1. empty blocks to flagged
		// 2. flagged to question mark
		// 3. question mark to blank

		if (mapControl.getCellByIndex(currentRow, currentColumn).isClickable()
				&& (mapControl.getCellByIndex(currentRow, currentColumn)
						.isEnabled() || mapControl.getCellByIndex(currentRow,
						currentColumn).isFlagged())) {
			mp.start(); // Sound on

			// case 1. set blank block to flagged
			if (!mapControl.getCellByIndex(currentRow, currentColumn)
					.isFlagged()
					&& !mapControl.getCellByIndex(currentRow, currentColumn)
							.isDoubted()) {
				mapControl.getCellByIndex(currentRow, currentColumn)
						.setFlagIcon(true);
				mapControl.getCellByIndex(currentRow, currentColumn).setFlag(
						true);
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() - 1);
				trapText.setText("" + GameData.getInstance().getTrapsRemain());

				return;
			}

			// case 2. set flagged to question mark
			if (!mapControl.getCellByIndex(currentRow, currentColumn)
					.isDoubted()) {
				mapControl.getCellByIndex(currentRow, currentColumn).setDoubt(
						true);
				mapControl.getCellByIndex(currentRow, currentColumn)
						.setFlagIcon(false);
				mapControl.getCellByIndex(currentRow, currentColumn)
						.setDoubtIcon(true);
				mapControl.getCellByIndex(currentRow, currentColumn).setFlag(
						false);
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() + 1);
				trapText.setText("" + GameData.getInstance().getTrapsRemain());
				return;
			}

			// case 3. change to blank square
			mapControl.getCellByIndex(currentRow, currentColumn)
					.clearAllIcons();
			mapControl.getCellByIndex(currentRow, currentColumn)
					.setDoubt(false);
			// if it is flagged then increment trap count
			if (mapControl.getCellByIndex(currentRow, currentColumn)
					.isFlagged()) {
				GameData.getInstance().setTrapsRemain(
						GameData.getInstance().getTrapsRemain() + 1);
				trapText.setText("" + GameData.getInstance().getTrapsRemain());
			}
			// remove flagged status
			mapControl.getCellByIndex(currentRow, currentColumn).setFlag(false);

		}
	}
}