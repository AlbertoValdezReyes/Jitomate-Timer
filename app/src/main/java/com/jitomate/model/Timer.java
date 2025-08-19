package com.jitomate.model;

public class Timer {
    private State TimerState;
    private long startTime;
    private long totalDurationInSeconds;
    private boolean isRunning;

    enum State {
        WORK,
        BREAK,
        STOPPED
    }

    public Timer(int hours, int minutes, int seconds) {
        this.totalDurationInSeconds = hours * 3600L + minutes * 60L + seconds;
        this.isRunning = false;
    }

    public Timer(int hours, int minutes) {
        this(hours, minutes, 0);
    }

    public Timer(int minutes) {
        this(0, minutes, 0);
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        TimerState = State.WORK;
    }

    public void stop() {
        this.isRunning = false;
    }

    public void reset() {
        this.isRunning = false;
        this.startTime = 0;
    }

    public boolean isFinished() {
        if (!isRunning) return false;
        return getRemainingTimeInSeconds() <= 0;
    }

    public long getRemainingTimeInSeconds() {
        if (!isRunning) return totalDurationInSeconds;

        long elapsedTimeInSeconds = (System.currentTimeMillis() - startTime) / 1000;
        return Math.max(0, totalDurationInSeconds - elapsedTimeInSeconds);
    }

    public String displayCurrentTimer() {
        long remainingTime = getRemainingTimeInSeconds();

        long hours = remainingTime / 3600;
        long minutes = (remainingTime % 3600) / 60;
        long seconds = remainingTime % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public State getTimerState() {
        return TimerState;
    }

    public void setTimerState(State state) {
        this.TimerState = state;
    }
}
