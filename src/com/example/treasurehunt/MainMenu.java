package com.example.treasurehunt;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 * This is a main menu of this game
 * @author group 8
 */
public class MainMenu extends Activity implements OnClickListener {

	/*
	 * Properties
	 */
	private MediaPlayer mp;
	Button btn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		btn = (Button) findViewById(R.id.settingBtn);

		mp = MediaPlayer.create(MainMenu.this, R.raw.sound1);
		mp.setLooping(true);
		mp.start();
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
	 * This code handle the activity
	 * 
	 * @author 8C Pham Duy Hung
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.newGameBtn:
			if (mp.isPlaying() && mp.isLooping()) {
				btn.setBackgroundResource(R.drawable.mute);
				mp.pause();
			}
			Intent openNewGame = new Intent(MainMenu.this, Game.class);
			openNewGame.putExtra("Level", "1");
			openNewGame.putExtra("Total Score", "0");
			startActivity(openNewGame);
			break;
		case R.id.continueBtn:

			break;

		case R.id.settingBtn:
			if (mp.isPlaying() && mp.isLooping()) {
				btn.setBackgroundResource(R.drawable.mute);
				mp.pause();
				try {
					mp.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
		}
	}
}
