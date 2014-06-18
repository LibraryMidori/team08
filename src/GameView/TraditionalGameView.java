package GameView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GameControllers.GameController;
import GameRule.SingletonSetupData;
import MapHolder.GameData;
import MapHolder.MapTradition;
import Timer.Timer;
import android.app.Activity;
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
import android.view.Menu;
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

import com.example.treasurehunt.MainMenu;
import com.example.treasurehunt.R;
import com.example.treasurehunt.Score;

public class TraditionalGameView extends Activity {

	private TableLayout map;
	// private Cell cells[][];
	private int numberOfRows = 0;
	private int numberOfColumns = 0;
	// private int trapsRemain = 0;
	/*
	 * private int level = 1; private int lives = 0; private int totalScore = 0;
	 */

	private MapTradition mapControl;
	private GameController gameControl;

	// Sound
	private MediaPlayer mp;

	// Pop up
	Handler handler;

	// Texts
	Typeface font, font2;
	TextView levelText, scoreText, timeText, livesText, finalScoreText,
			finalTimeText, trapText;

	// UI
	ImageView mImgViewResult;

	// Save Score
	private SharedPreferences gamePrefs;
	public static final String GAME_PREFS = "ArithmeticFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				String val1 = extras.getString("Level");
				GameData.getInstance().setLevel(Integer.parseInt(val1));
				val1 = extras.getString("Total Score");
				GameData.getInstance().setTotalScore(Integer.parseInt(val1));
				val1 = extras.getString("Lives");
				GameData.getInstance().setLives(Integer.parseInt(val1));
				return;
			}

			Toast.makeText(this, "Cannot load the game", Toast.LENGTH_SHORT)
					.show();
			Intent backToMainMenu = new Intent(TraditionalGameView.this,
					MainMenu.class);
			startActivity(backToMainMenu);
		} catch (Exception e) {
			Toast.makeText(this, "Cannot load the game", Toast.LENGTH_SHORT)
					.show();
			Intent backToMainMenu = new Intent(TraditionalGameView.this,
					MainMenu.class);
			startActivity(backToMainMenu);
		}

		// Share preference of Game class
		gamePrefs = getSharedPreferences(GAME_PREFS, 0);

		gameControl = new GameController(GameData.getInstance().getLevel(),
				GameData.getInstance().getTotalScore(), GameData.getInstance()
						.getLives());

		gameController(GameData.getInstance().getLevel(), GameData
				.getInstance().getTotalScore(), GameData.getInstance()
				.getLives());
		initView();
		startNewGame();
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

	private void initView() {

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

		levelText.setText("LEVEL " + GameData.getInstance().getLevel());
		scoreText.setText("" + GameData.getInstance().getTotalScore());
		livesText.setText("" + GameData.getInstance().getLives());
		trapText.setText("" + GameData.getInstance().getTrapsRemain());

		mImgViewResult = (ImageView) findViewById(R.id.img_result);

	}

	private void gameController(int _level, int _score, int _lives) {
		int maxTime = SingletonSetupData.getInstance().getMaxTime();
		int minTraps = SingletonSetupData.getInstance().getMinTraps();

		// Default setting
		GameData.getInstance().setGameOver(false);
		GameData.getInstance().setGameStart(false);
		GameData.getInstance().setMapGen(false);

		numberOfRows = 16;
		numberOfColumns = 30;

		// Tracking time
		// clock = new Handler();

		switch (_level) {
		case 1:
			setUpGame(maxTime, minTraps - 50, _score, _lives);
			break;
		case 2:
			setUpGame(maxTime, minTraps + _level, _score, _lives);
			break;
		case 3:
			setUpGame(maxTime, minTraps + _level, _score, _lives);
			break;
		case 4:
			setUpGame(maxTime - 60, minTraps + _level, _score, _lives);
			break;
		case 5:
			setUpGame(maxTime - 60, minTraps + _level, _score, _lives);
			break;
		case 6:
			setUpGame(maxTime - 60, minTraps + _level, _score, _lives);
			break;
		case 7:
			setUpGame(maxTime - 60 * 2, minTraps + _level, _score, _lives);
			break;
		case 8:
			setUpGame(maxTime - 60 * 2, minTraps + _level, _score, _lives);
			break;
		case 9:
			setUpGame(maxTime - 60 * 2, minTraps + _level, _score, _lives);
			break;
		case 10:
			setUpGame(maxTime - 60 * 3, minTraps + _level, _score, _lives);
			break;
		case 11:
			setUpGame(maxTime - 60 * 3, minTraps + _level, _score, _lives);
			break;
		case 12:
			setUpGame(maxTime - 60 * 3, minTraps + _level, _score, _lives);
			break;
		case 13:
			setUpGame(maxTime - 60 * 4, minTraps + _level, _score, _lives);
			break;
		case 14:
			setUpGame(maxTime - 60 * 4, minTraps + _level, _score, _lives);
			break;
		case 15:
			setUpGame(maxTime - 60 * 4, minTraps + _level, _score, _lives);
			break;
		default:
			break;
		}
	}

	private void setUpGame(int playTime, int numberOfTraps, int score,
			int _lives) {
		mapControl = new MapTradition(16, 30, numberOfTraps);
		GameData.getInstance().setTrapsRemain(numberOfTraps);
		GameData.getInstance().setTotalScore(score);
		GameData.getInstance().setLives(_lives);
	}

	private void startNewGame() {
		createMap();
		showMap();

		GameData.getInstance().setGameOver(false);
		GameData.getInstance().setGameStart(false);
		timeText.setText("" + Timer.getInstance().getTimer());
	}

	private void createMap() {
		mapControl.createMap(this);
	}

	private void showMap() {
		int cellWidth = SingletonSetupData.getInstance().getCellWidth();
		int cellPadding = SingletonSetupData.getInstance().getCellPadding();

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

	private void winGame() {
		// reset all stuffs
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
					// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub
					GameData.getInstance().setLevel(
							GameData.getInstance().getLevel() + 1);

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

	private void finishGame(int currentRow, int currentColumn) {
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
											input.setText("Playername");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveGameState(int _level, int _score, int _lives) {
		SharedPreferences.Editor gameStateEdit = gamePrefs.edit();

		gameStateEdit.putString("saveGame", _level + " - " + _score + " - "
				+ _lives);
		gameStateEdit.commit();
	}

}
