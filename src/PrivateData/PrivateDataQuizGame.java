package PrivateData;

public class PrivateDataQuizGame {
	private int numberOfRows = 0;
	private int numberOfColumns = 0;
	private int totalTraps = 0;

	public PrivateDataQuizGame(int numberOfRows, int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public int getTotalTraps() {
		return totalTraps;
	}

	public void setTotalTraps(int totalTraps) {
		this.totalTraps = totalTraps;
	}

}
