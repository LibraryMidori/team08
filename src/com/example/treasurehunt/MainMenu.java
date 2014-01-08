package com.example.treasurehunt;

import android.app.Activity;
<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> 66c04cc7da005f9fb9a9ae79cda101575ba54064
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
<<<<<<< HEAD
=======

/*
 * This is a main menu of this game
 * @author group 8
 */
public class MainMenu extends Activity implements OnClickListener {
>>>>>>> 66c04cc7da005f9fb9a9ae79cda101575ba54064

	/*
	 * Properties
	 */
	Button newGameBtn, loadGameBtn, rankingBtn, recordBtn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		// @author 8B Pham Hung Cuong
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

<<<<<<< HEAD
=======
	/*
	 * Initial View
	 * 
	 * @author 8B Pham Hung Cuong
	 */
	public void initView() {
		newGameBtn = (Button) findViewById(R.id.newGameBtn);
		loadGameBtn = (Button) findViewById(R.id.loadGameBtn);
		rankingBtn = (Button) findViewById(R.id.rankingBtn);
		recordBtn = (Button) findViewById(R.id.recordBtn);
	}

	/*
	 * This code handle the activity
	 * @author 8C Pham Duy Hung
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.newGameBtn:
			Intent openNewGame = new Intent(MainMenu.this, Game.class);
			startActivity(openNewGame);
			break;
		case R.id.loadGameBtn:

			break;
		case R.id.rankingBtn:

			break;
		case R.id.recordBtn:

			break;
		}
	}
>>>>>>> 66c04cc7da005f9fb9a9ae79cda101575ba54064
}
