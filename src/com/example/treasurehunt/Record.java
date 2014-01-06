package com.example.treasurehunt;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Record extends Activity {
//	private String playerName;
//	private Integer score;
//	private Integer level;

	public String loadRecord() {

		InputStream inputStream = getResources().openRawResource(R.raw.test);
		System.out.println(inputStream);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int i;
		try {
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArrayOutputStream.toString();
	}

	public boolean saveRecord(String playerName, Integer score, Integer level) {
		try {
			FileOutputStream out = openFileOutput("dulieu.txt",
					Context.MODE_PRIVATE);

			String pos = playerName;
			pos += ":";
			pos += score;
			pos += ":";
			pos += level;

			out.write(pos.getBytes());
			out.close();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Can't write record file!!!", Toast.LENGTH_SHORT).show();
		}
		return true;
	}
}
