package GameRule;

public class SingletonSetupData {
	private static SingletonSetupData data = new SingletonSetupData();
	private final int minTraps = 84;
	private final int maxTime = 480;

	private final int cellWidth = 34;
	private final int cellPadding = 2;

	private SingletonSetupData() {

	}

	public static SingletonSetupData getInstance() {
		return data;
	}

	public int getMinTraps() {
		return minTraps;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public int getCellPadding() {
		return cellPadding;
	}

}
