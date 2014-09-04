package com.example.treasurehunt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Controller.TraditionalGameController;
import GameRule.SingletonSetupData;
import MapHolder.GameData;
import PrivateData.PrivateDataQuizGame;
import Timer.Timer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class TraditionalGameView extends AbstractGameView {
	public static final String GAME_PREFS = "ArithmeticFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				GameData.getInstance().levelUp();
			} else {
				Toast.makeText(this, "Cannot load the game", Toast.LENGTH_SHORT)
						.show();
				Intent backToMainMenu = new Intent(TraditionalGameView.this,
						MainMenu.class);
				startActivity(backToMainMenu);
			}
		} catch (Exception e) {
			Toast.makeText(this, "Cannot load the game", Toast.LENGTH_SHORT)
					.show();
			Intent backToMainMenu = new Intent(TraditionalGameView.this,
					MainMenu.class);
			startActivity(backToMainMenu);
		}

		// Share preference of Game class
		gamePrefs = getSharedPreferences(GAME_PREFS, 0);

		createdGameView();
	}

	protected void initView() {
		mp = MediaPlayer.create(TraditionalGameView.this, R.raw.flag);

		map = (TableLayout) findViewById(R.id.Map);
		// recordSaver = new Record();

		font = Typeface.createFromAsset(getBaseContext().getAssets(),
				"fonts/FRANCHISE-BOLD.TTF");
		font2 = Typeface.createFromAsset(getBaseContext().getAssets(),
				"fonts/Sketch_Block.ttf");
		levelText = (TextView) findViewById(R.id.levelText);
		levelText.setTypeface(font2);
		scoreText = (TextView) findViewById(R.id.scoreText);
		scoreText.setTypeface(font2);
		timeText = (TextView) findViewById(R.id.timeText);
		timeText.setTypeface(font2);
		livesText = (TextView) findViewById(R.id.livesText);
		livesText.setTypeface(font2);
		trapText = (TextView) findViewById(R.id.trapText);
		trapText.setTypeface(font2);

		setText();

		mImgViewResult = (ImageView) findViewById(R.id.img_result);
	}

	protected void gameController() {
		int _level = GameData.getInstance().getLevel();

		// Default setting
		GameData.getInstance().setGameOver(false);
		GameData.getInstance().setGameStart(false);
		GameData.getInstance().setMapGen(false);

		quizData = new PrivateDataQuizGame(16, 30);
		controller = new TraditionalGameController();

		mapControl = controller.gameController(_level, quizData);

	}

	protected void showMap() {
		int cellWidth = SingletonSetupData.getInstance().getCellWidth();
		int cellPadding = SingletonSetupData.getInstance().getCellPadding();
		int numberOfRows = quizData.getNumberOfRows();
		int numberOfColumns = quizData.getNumberOfColumns();

		// remember we will not show 0th and last Row and Columns
		// they are used for calculation purposes only
		for (int row = 1; row < numberOfRows + 1; row++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams(
					(cellWidth + 2 * cellPadding) * numberOfColumns, cellWidth
							+ 2 * cellPadding));

			for (int column = 1; column < numberOfColumns + 1; column++) {
				mapControl.getCellByIndex(row, column).setLayoutParams(
						new LayoutParams(cellWidth + 2 * cellPadding, cellWidth
								+ 2 * cellPadding));
				mapControl.getCellByIndex(row, column).setPadding(cellPadding,
						cellPadding, cellPadding, cellPadding);
				tableRow.addView(mapControl.getCellByIndex(row, column));
			}
			map.addView(tableRow, new TableLayout.LayoutParams(
					(cellWidth + 2 * cellPadding) * numberOfColumns, cellWidth
							+ 2 * cellPadding));
		}
	}

	protected void winGame() {
		// reset all stuffs
		mapControl.winGame();
		Timer.getInstance().stopTimer();
		GameData.getInstance().setGameFinish(true);
		GameData.getInstance().setGameStart(false);
		GameData.getInstance().setTrapsRemain(0);
		GameData.getInstance().setTotalScore(
				GameData.getInstance().getTotalScore() + 1000);
		scoreText.setText("" + GameData.getInstance().getTotalScore());

		// disable all buttons
		// set flagged all un-flagged blocks

		handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mImgViewResult.setBackgroundResource(R.drawable.congrat);
				mImgViewResult.setVisibility(View.VISIBLE);
				mImgViewResult.bringToFront();
				mImgViewResult.postDelayed(new Runnable() {

					@Override
					public void run() {
						mImgViewResult.setVisibility(View.GONE);
						showWinPopUp();
						handler.removeCallbacks(this);
					}
				}, 2000);
			}
		}, 500);

	}

	private void showWinPopUp() {
		if (!GameData.getInstance().isGameOver()) {
			GameData.getInstance().setGameOver(true);
			final Dialog popup = new Dialog(TraditionalGameView.this);
			popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
			popup.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			popup.setContentView(R.layout.win_popup);
			// Set dialog title

			// popup.setTitle("Say something");
			popup.setCancelable(false);

			finalScoreText = (TextView) popup.findViewById(R.id.finalScore);
			finalScoreText.setTypeface(font);
			finalScoreText.setText("" + GameData.getInstance().getTotalScore());

			finalTimeText = (TextView) popup.findViewById(R.id.finalTime);
			finalTimeText.setTypeface(font);
			finalTimeText.setText("" + Timer.getInstance().getTimer());

			popup.dismiss();
			popup.show();

			Button saveRecordBtn = (Button) popup
					.findViewById(R.id.save_record);
			saveRecordBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(
							TraditionalGameView.this);

					alert.setTitle("Enter your name");

					// Set an EditText view to get user
					final EditText input = new EditText(
							TraditionalGameView.this);
					input.setText("Playername");
					alert.setView(input);

					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String value = input.getText().toString();

									setHighScore(value, GameData.getInstance()
											.getTotalScore(), GameData
											.getInstance().getLevel());

								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// Canceled.
								}
							});
					alert.show();
				}
			});

			Button quitToMenuBtn = (Button) popup
					.findViewById(R.id.quit_to_menu);
			quitToMenuBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popup.dismiss();
					Intent backToMenu = new Intent(TraditionalGameView.this,
							MainMenu.class);
					startActivity(backToMenu);
					finish();
				}
			});

			Button nextLevelBtn = (Button) popup.findViewById(R.id.next_level);
			nextLevelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					GameData.getInstance().setLevel(
							GameData.getInstance().getLevel() + 1);

					Log.e("8C>>>>>>>>>>>>>>", "Level up: "
							+ GameData.getInstance().getLevel());
					if (GameData.getInstance().getLevel() == 5
							|| GameData.getInstance().getLevel() == 10) {
						Intent nextLevel = new Intent(TraditionalGameView.this,
								QuizGameView.class);
						nextLevel.putExtra("Level", ""
								+ GameData.getInstance().getLevel());
						nextLevel.putExtra("Total Score", ""
								+ GameData.getInstance().getTotalScore());
						nextLevel.putExtra("Lives", ""
								+ GameData.getInstance().getLives());
						startActivity(nextLevel);
						finish();
					} else if (GameData.getInstance().getLevel() < 16) {
						Intent nextLevel = new Intent(TraditionalGameView.this,
								TraditionalGameView.class);
						nextLevel.putExtra("Level", ""
								+ GameData.getInstance().getLevel());
						nextLevel.putExtra("Total Score", ""
								+ GameData.getInstance().getTotalScore());
						nextLevel.putExtra("Lives", ""
								+ GameData.getInstance().getLives());
						startActivity(nextLevel);
						finish();
					} else {
						Toast.makeText(TraditionalGameView.this,
								"Congratulation, you win!!", Toast.LENGTH_SHORT)
								.show();

						Intent backToMainMenu = new Intent(
								TraditionalGameView.this, MainMenu.class);
						startActivity(backToMainMenu);
						finish();
					}
				}
			});

			Button postToFbBtn = (Button) popup.findViewById(R.id.post_to_fb);
			postToFbBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
		}
	}

	protected void finishGame(int currentRow, int currentColumn) {
		Timer.getInstance().stopTimer(); // stop timer
		GameData.getInstance().setGameStart(false);

		mapControl.finishGame(currentRow, currentColumn);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (Timer.getInstance().getTimer() <= 0) {
					mImgViewResult.setBackgroundResource(R.drawable.timeout);
				}
				mImgViewResult.setVisibility(View.VISIBLE);
				mImgViewResult.bringToFront();
				mImgViewResult.postDelayed(new Runnable() {

					@Override
					public void run() {
						mImgViewResult.setVisibility(View.GONE);

						if (!GameData.getInstance().isGameOver()) {
							GameData.getInstance().setGameOver(true); // mark
																		// game
																		// as
																		// over
							final Dialog popup = new Dialog(
									TraditionalGameView.this);
							popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
							popup.getWindow()
									.setBackgroundDrawable(
											new ColorDrawable(
													android.graphics.Color.TRANSPARENT));
							popup.setContentView(R.layout.finish_popup);
							// Set dialog title
							// TODO time up

							// popup.setTitle("Say something");
							popup.setCancelable(false);

							finalScoreText = (TextView) popup
									.findViewById(R.id.finalScore);
							finalScoreText.setTypeface(font);
							finalScoreText.setText(""
									+ GameData.getInstance().getTotalScore());

							finalTimeText = (TextView) popup
									.findViewById(R.id.finalTime);
							finalTimeText.setTypeface(font);
							finalTimeText.setText(""
									+ Timer.getInstance().getTimer());

							popup.show();

							Button saveRecordBtn = (Button) popup
									.findViewById(R.id.save_record);
							saveRecordBtn
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											AlertDialog.Builder alert = new AlertDialog.Builder(
													TraditionalGameView.this);

											alert.setTitle("Enter your name");

											// Set an EditText view to get user
											// input
											final EditText input = new EditText(
													TraditionalGameView.this);
											input.setText("");
											alert.setView(input);

											alert.setPositiveButton(
													"Ok",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int whichButton) {
															String value = input
																	.getText()
																	.toString();
															// Do something with
															// value!
															setHighScore(
																	value,
																	GameData.getInstance()
																			.getTotalScore(),
																	GameData.getInstance()
																			.getLevel());

														}
													});

											alert.setNegativeButton(
													"Cancel",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int whichButton) {
															// Canceled.
														}
													});

											alert.show();
										}
									});

							Button quitToMenuBtn = (Button) popup
									.findViewById(R.id.quit_to_menu);
							quitToMenuBtn
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											GameData.getInstance().setDefault();
											popup.dismiss();
											Intent backToMenu = new Intent(
													TraditionalGameView.this,
													MainMenu.class);
											startActivity(backToMenu);
											finish();
										}
									});

							Button postToFbBtn = (Button) popup
									.findViewById(R.id.post_to_fb);
							postToFbBtn
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											popup.dismiss();
										}
									});
						}
					}
				}, 2000);
			}
		}, 500);
	}

	/*
	 * Set high score
	 * 
	 * @author 8A Tran Trong Viet
	 * 
	 * @param sc: savedInstanceState: the state of previous game
	 */
	public void setHighScore(String playerName, int score, int level) {
		try {
			if (score > 0) {

				SharedPreferences.Editor scoreEdit = gamePrefs.edit();
				// get existing scores
				String scores = gamePrefs.getString("highScores", "");

				// check for scores
				if (scores.length() > 0) {

					List<Score> scoreStrings = new ArrayList<Score>();
					String[] exScores = scores.split("\\|");

					// add score object for each
					for (String eSc : exScores) {
						String[] parts = eSc.split(" - ");
						scoreStrings
								.add(new Score(parts[0], Integer
										.parseInt(parts[1]), Integer
										.parseInt(parts[2])));
					}

					// new score
					Score newScore = new Score(playerName, score, level);
					scoreStrings.add(newScore);
					Collections.sort(scoreStrings);

					// get top ten
					StringBuilder scoreBuild = new StringBuilder("");
					for (int s = 0; s < scoreStrings.size(); s++) {
						if (s >= 10)
							break;
						if (s > 0)
							scoreBuild.append("|");
						scoreBuild.append(scoreStrings.get(s).getScoreText());
					}
					// write to prefs
					scoreEdit.putString("highScores", scoreBuild.toString());
					scoreEdit.commit();

				} else {
					// no existing scores
					scoreEdit.putString("highScores", "" + playerName + " - "
							+ score + " - " + level);
					scoreEdit.commit();
				}

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
