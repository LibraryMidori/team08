package CellPackage;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.treasurehunt.R;

public class Cell extends Button {
	private CellDataHolder dataHolder;

	public Cell(Context context) {
		super(context);
		setDefaults();
	}

	public Cell(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDefaults();
	}

	public Cell(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setDefaults();
	}

	private void setDefaults() {
		dataHolder = new CellDataHolder();
		dataHolder.setCovered(true);
		dataHolder.setTrapped(false);
		dataHolder.setFlagged(false);
		dataHolder.setDoubt(false);
		dataHolder.setClickable(true);
		dataHolder.setNumberOfTrapInSurrounding(0);

		Random r = new Random();
		switch (r.nextInt() % 2) {
		case 0:
			this.setBackgroundResource(R.drawable.cell1);
			break;
		case 1:
			this.setBackgroundResource(R.drawable.cell2);
			break;
		default:
			this.setBackgroundResource(R.drawable.cell1);
			break;
		}

		// this.setBackgroundResource(R.drawable.square_blue);
		// setBoldFont();
	}

	public boolean isCovered() {
		return dataHolder.isCovered();
	}

	public boolean hasTreasure() {
		return dataHolder.isTreasure();
	}

	public boolean hasTrap() {
		return dataHolder.isTrapped();
	}

	public boolean isFlagged() {
		return dataHolder.isFlagged();
	}

	public boolean isDoubted() {
		return dataHolder.isDoubt();
	}

	public boolean isClickable() {
		return dataHolder.isClickable();
	}

	public void setFlag(boolean flagged) {
		dataHolder.setFlagged(flagged);
		if (dataHolder.isFlagged()) {
			dataHolder.setClickable(true);
		}
	}

	public void setDoubt(boolean questionMarked) {
		dataHolder.setDoubt(questionMarked);
		if (dataHolder.isDoubt()) {
			dataHolder.setClickable(true);
		}
	}

	public void setNumberOfSurroundingTraps(int number) {
		// this.setBackgroundResource(R.drawable.square_grey);
		this.setBackgroundResource(R.drawable.empty);
		updateNumber(number);
	}

	public void setTreasureIcon(boolean enabled) {
		this.setBackgroundResource(R.drawable.treasure);
	}

	public void setTrapIcon(boolean enabled) {
		if (!enabled) {
			// this.setBackgroundResource(R.drawable.square_grey);
			this.setBackgroundResource(R.drawable.trap);
			this.setTextColor(Color.RED);
		} else {
			this.setTextColor(Color.BLACK);
		}
	}

	public void setFlagIcon(boolean enabled) {
		this.setBackgroundResource(R.drawable.flag);

		// if (!enabled) {
		// // this.setBackgroundResource(R.drawable.square_grey);
		// this.setBackgroundResource(R.drawable.flag);
		// this.setTextColor(Color.RED);
		// } else {
		// this.setTextColor(Color.BLACK);
		// }
	}

	public void setDoubtIcon(boolean enabled) {
		// this.setText("?");
		// this.setBackgroundResource(R.drawable.square_blue);
		this.setBackgroundResource(R.drawable.doubt);

		if (!enabled) {
			this.setTextColor(Color.RED);
		} else {
			this.setTextColor(Color.BLACK);
		}
	}

	public void clearAllIcons() {
		// this.setText("");
		this.setBackgroundResource(R.drawable.cell1);
	}

	public void setCellAsDisabled(boolean enabled) {
		if (!enabled) {
			// this.setBackgroundResource(R.drawable.square_grey);
			this.setBackgroundResource(R.drawable.empty);

		} else {
			// this.setBackgroundResource(R.drawable.square_blue);
			// this.setBackgroundResource(R.drawable.empty);
		}

	}

	public void disableCell() {
		dataHolder.setClickable(false);
	}

	public void enableCell() {
		dataHolder.setClickable(true);
	}

	public void setTreasure() {
		dataHolder.setTreasure(true);
	}

	public void setTrap() {
		dataHolder.setTrapped(true);
	}

	public void OpenCell() {
		// cannot uncover a trap which is not covered
		if (!dataHolder.isCovered())
			return;

		// setCellAsDisabled(false);
		dataHolder.setCovered(false);

		if (dataHolder.getNumberOfTrapInSurrounding() == 0) {
			dataHolder.setClickable(false);
		}

		// check if it has trap
		if (hasTrap()) {
			setTrapIcon(false);
		}
		// update with the nearby trap count
		else {
			setNumberOfSurroundingTraps(dataHolder
					.getNumberOfTrapInSurrounding());
		}
	}

	public void updateNumber(int text) {
		if (text != 0) {
			// this.setText(Integer.toString(text));

			// select different color for each number
			// we have 1 - 8 trap count
			switch (text) {
			case 1:
				this.setBackgroundResource(R.drawable.c1);
				// this.setTextColor(Color.BLUE);
				break;
			case 2:
				this.setBackgroundResource(R.drawable.c2);
				// this.setTextColor(Color.rgb(0, 100, 0));
				break;
			case 3:
				this.setBackgroundResource(R.drawable.c3);
				// this.setTextColor(Color.RED);
				break;
			case 4:
				this.setBackgroundResource(R.drawable.c4);
				// this.setTextColor(Color.rgb(85, 26, 139));
				break;
			case 5:
				this.setBackgroundResource(R.drawable.c5);
				// this.setTextColor(Color.rgb(139, 28, 98));
				break;
			case 6:
				this.setBackgroundResource(R.drawable.c6);
				// this.setTextColor(Color.rgb(238, 173, 14));
				break;
			case 7:
				this.setBackgroundResource(R.drawable.c7);
				// this.setTextColor(Color.rgb(47, 79, 79));
				break;
			case 8:
				this.setBackgroundResource(R.drawable.c8);
				// this.setTextColor(Color.rgb(71, 71, 71));
				break;
			case 9:
				// this.setBackgroundResource(R.drawable.empty);
				// this.setTextColor(Color.rgb(205, 205, 0));
				break;
			}
		}
	}

	public void triggerTrap() {
		setTrapIcon(true);
	}

	public int getNumberOfTrapsInSurrounding() {
		return dataHolder.getNumberOfTrapInSurrounding();
	}

	@Override
	public String toString() {
		if (dataHolder.isTrapped()) {
			return "This is a trap "
					+ dataHolder.getNumberOfTrapInSurrounding();
		} else {
			return "This is not a trap "
					+ dataHolder.getNumberOfTrapInSurrounding();
		}
	}

	public void setNumberOfTrapsInSurrounding(int number) {
		dataHolder.setNumberOfTrapInSurrounding(number);
	}
}
