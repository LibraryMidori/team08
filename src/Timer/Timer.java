package Timer;

import android.os.Handler;

import com.example.treasurehunt.IGameView;

public class Timer {
	private static Timer clockCounter = new Timer();
	private int timer;
	private Handler clock;
	private IGameView observer;
	private boolean isRunning = false;

	private Timer() {
		clock = new Handler();
	}

	public static Timer getInstance() {
		return clockCounter;
	}

	public void decreaseTimer() {
		this.timer--;
		if (this.timer <= 0) {
			this.timer = 0;
		}
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public void startTimer() {
		clock.removeCallbacks(updateTimeElasped);
		clock.postDelayed(updateTimeElasped, 1000);
		isRunning = true;
	}

	public void stopTimer() {
		// disable call backs
		clock.removeCallbacks(updateTimeElasped);
		isRunning = false;
	}

	public void registed(IGameView observer) {
		this.observer = observer;
	}

	private Runnable updateTimeElasped = new Runnable() {

		@Override
		public void run() {
			long currentMilliseconds = System.currentTimeMillis();
			if (isRunning) {
				decreaseTimer();
				notifyToObserver();
			}

			clock.postAtTime(this, currentMilliseconds);
			// notify to call back after 1 seconds
			// basically to remain in the timer loop
			clock.postDelayed(updateTimeElasped, 1000);
		}
	};

	private void notifyToObserver() {
		observer.updateTime();
	}
}