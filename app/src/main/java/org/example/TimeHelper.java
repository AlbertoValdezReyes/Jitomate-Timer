package org.example;
import java.util.TimerTask;

class TimeHelper extends TimerTask {
	private int countdown = 0;

	public void run() {
		System.out.println("Timer ran " + countdown++);
	}
}

