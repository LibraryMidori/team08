package com.example.treasurehunt;

/*
 * Class Score
 * 
 * @author 8A Tran Trong Viet
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Record extends Activity {

	public final String GAME_PREFS = "ArithmeticFile";
	private SharedPreferences gamePrefs;

	/*
	 * Show high score
	 * 
	 * @author 8A Tran Trong Viet
	 * 
	 * @param sc: savedInstanceState: the state of previous game
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		initView();

		for (int i = 0; i < 10; i++) {
			setHighScore("0", i + 1, 0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	/*
	 * Initial view
	 * 
	 * @author 8A Tran Trong Viet
	 */
	private void initView() {

		TextView scoreView = (TextView) findViewById(R.id.high_scores_list);

		// get shared prefs
		gamePrefs = getSharedPreferences(GAME_PREFS, 0);
		// get scores
		String[] savedScores = gamePrefs.getString("highScores", "").split(
				"\\|");

		// build string
		StringBuilder scoreBuild = new StringBuilder("");
		for (String score : savedScores) {
			scoreBuild.append(score + "\n");
		}

		scoreView.setText(scoreBuild.toString());
	}

	/*
	 * Set high score
	 * 
	 * @author 8A Tran Trong Viet
	 * 
	 * @param sc: savedInstanceState: the state of previous game
	 */
	private void setHighScore(String playerName, int exScore, int level) {
		if (exScore > 0) {
			// we have a valid score
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
					scoreStrings.add(new Score(parts[0], Integer
							.parseInt(parts[1]), Integer.parseInt(parts[2])));
				}

				// new score
				Score newScore = new Score(playerName, exScore, level);
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
						+ exScore + " - " + level);
				scoreEdit.commit();
			}
		}
	}
}
