package com.example.treasurehunt;

import android.content.Context;
import android.widget.Button;

/*
 * @author: group 8
 */

public class Cell extends Button {

	/*
	 * Properties
	 */
	private boolean isTraped; // The cell is trap or not
	private boolean isTreasure; // The cell is treasure or not
	private boolean isFlaged; // The cell is flag
	private boolean isDoubt; // The cell is marked as doubt

	/*
	 * Constructor
	 * 
	 * @author: 8B Pham Hung Cuong
	 * 
	 * @param: context - context
	 */
	public Cell(Context context) {
		super(context);
	}

}
