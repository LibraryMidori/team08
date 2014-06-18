package CellPackage;

public class CellDataHolder {
	private boolean isTrapped; // The cell is trap or not
	private boolean isTreasure; // The cell is treasure or not
	private boolean isFlagged; // The cell is flag
	private boolean isDoubt; // The cell is marked as doubt
	private boolean isCovered; // is cell covered yet
	private boolean isClickable; // can cell accept click events
	private int numberOfTrapInSurrounding; // number of traps in nearby cells

	public CellDataHolder() {
		
	}
	
	public boolean isTrapped() {
		return isTrapped;
	}

	public void setTrapped(boolean isTrapped) {
		this.isTrapped = isTrapped;
	}

	public boolean isTreasure() {
		return isTreasure;
	}

	public void setTreasure(boolean isTreasure) {
		this.isTreasure = isTreasure;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean isDoubt() {
		return isDoubt;
	}

	public void setDoubt(boolean isDoubt) {
		this.isDoubt = isDoubt;
	}

	public boolean isCovered() {
		return isCovered;
	}

	public void setCovered(boolean isCovered) {
		this.isCovered = isCovered;
	}

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}

	public int getNumberOfTrapInSurrounding() {
		return numberOfTrapInSurrounding;
	}

	public void setNumberOfTrapInSurrounding(int numberOfTrapInSurrounding) {
		this.numberOfTrapInSurrounding = numberOfTrapInSurrounding;
	}

}
