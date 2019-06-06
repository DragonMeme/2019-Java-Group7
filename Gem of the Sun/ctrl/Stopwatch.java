package ctrl;

public class Stopwatch {
	private long startTimer = 0;
	private long initTimer = 0;
	private long stopTimer = 0;
	boolean running = false, pause = false;

	public void start() {
		if (!running) {
			this.startTimer = pause ? System.currentTimeMillis() - (stopTimer - initTimer) : System.currentTimeMillis();
			initTimer = startTimer;
			pause = false;
			running = true;
		}
	}

	public void stop() {
		if (running) {
			this.stopTimer = System.currentTimeMillis();
			running = false;
		}
	}

	public void pause() {
		if (running) {
			this.stopTimer = System.currentTimeMillis();
			pause = true;
			running = false;
		}
	}

	public int secondsPassed() {
		long elapsed;
		if (running)
			elapsed = (System.currentTimeMillis() - startTimer) / 1000;
		else
			elapsed = (stopTimer - startTimer) / 1000;
		return (int) elapsed;
	}
}
