package com.example.treasurehunt;

/*
 * Class Score
 * 
 * @author 8A Tran Trong Viet
 * 
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Record extends Activity {

	public final String GAME_PREFS = "ArithmeticFile";

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
		SharedPreferences scorePrefs = getSharedPreferences(Game.GAME_PREFS, 0);
		String[] savedScores = scorePrefs.getString("highScores", "").split(
				"\\|");

		// build string
		StringBuilder scoreBuild = new StringBuilder("");
		for (String score : savedScores) {
			scoreBuild.append(score + "\n");
		}

		scoreView.setText(scoreBuild.toString());
	}
}
