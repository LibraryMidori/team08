package PrivateData;

public class ConfigSizeData {
	private int cellWidth = 34; // DEFAULT
	private int cellPadding = 2; // DEFAULT

	public ConfigSizeData() {

	}

	public ConfigSizeData(int cellWidth, int cellPadding) {
		this.cellPadding = cellPadding;
		this.cellWidth = cellWidth;
	}

	public int getCellWidth() {
		return this.cellWidth;
	}

	public int getCellPadding() {
		return this.cellPadding;
	}
}
