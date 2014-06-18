package com.example.treasurehunt;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity implements OnClickListener {

	private MediaPlayer mp;
	private Button btn;
	private int level = 1, score = 0, lives = 3;

	public final String GAME_PREFS = "ArithmeticFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		btn = (Button) findViewById(R.id.settingBtn);

		mp = MediaPlayer.create(MainMenu.this, R.raw.sound1);
		mp.setLooping(true);
		mp.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newGameBtn:
			if (mp.isPlaying() && mp.isLooping()) {
				btn.setBackgroundResource(R.drawable.mute);
				mp.pause();
			}

			Intent openNewGame = new Intent(MainMenu.this,
					TraditionalGameView.class);
			openNewGame.putExtra("Level", "1");
			openNewGame.putExtra("Total Score", "0");
			openNewGame.putExtra("Lives", "3");
			try {
				startActivity(openNewGame);
			} catch (Exception ex) {
				Toast dialog = Toast.makeText(MainMenu.this, "Shit!!!!",
						Toast.LENGTH_SHORT);
				dialog.setGravity(Gravity.CENTER, 0, 0);
				dialog.setDuration(2000);
				dialog.show();
			}
			break;

		case R.id.continueBtn:
			if (mp.isPlaying() && mp.isLooping()) {
				btn.setBackgroundResource(R.drawable.mute);
				mp.pause();
			}

			SharedPreferences gameSavePrefs = getSharedPreferences(
					TraditionalGameView.GAME_PREFS, 0);

			String savedGame1 = gameSavePrefs.getString("saveGame", "");

			if (savedGame1.length() > 0) {
				String[] parts = savedGame1.split(" - ");

				level = Integer.parseInt(parts[0]);
				score = Integer.parseInt(parts[1]);
				lives = Integer.parseInt(parts[2]);

				// clear the saved game state
				gameSavePrefs.edit().putString("saveGame", "").commit();

				// load saved state to game play
				Intent openContinueGame = new Intent(MainMenu.this,
						TraditionalGameView.class);
				openContinueGame.putExtra("Level", "" + level);
				openContinueGame.putExtra("Total Score", "" + score);
				openContinueGame.putExtra("Lives", "" + lives);
				startActivity(openContinueGame);
			} else {
				Toast dialog = Toast.makeText(MainMenu.this,
						"There 's no game to continue !!!", Toast.LENGTH_SHORT);
				dialog.setGravity(Gravity.CENTER, 0, 0);
				dialog.setDuration(2000);
				dialog.show();
			}
			break;

		case R.id.settingBtn:
			if (mp.isPlaying() && mp.isLooping()) {
				btn.setBackgroundResource(R.drawable.mute);
				mp.pause();
				try {
					mp.prepare();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mp.seekTo(0);
			} else {
				btn.setBackgroundResource(R.drawable.sound);
				mp.start();
			}
			break;

		case R.id.recordBtn:
			Intent openRecord = new Intent(MainMenu.this, Record.class);
			startActivity(openRecord);
			break;
		case R.id.instructionBtn:
			Intent instruction = new Intent(MainMenu.this, Instruction.class);
			startActivity(instruction);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
	}

}
