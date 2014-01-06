package com.example.treasurehunt;

import android.content.Context;
import android.widget.Button;

public class Cell extends Button {
	private boolean isTraped; 			// The cell is trap or not
	private boolean isTreasure;			// The cell is treasure or not
	private boolean isFlaged;			// The cell is flag
	private boolean isDoubt;			// The cell is marked as doubt
	
	public Cell(Context context) {
		super(context);
	}

}
