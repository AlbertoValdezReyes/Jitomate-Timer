package com.jitomate.model;

public class Timer {
    /* Specifies the state the timer is currently on */
    public enum State {
        FOCUS,
        SHORT_BREAK,
        LONG_BREAK,
        STOPPED
    }

    /* Current state */
    private State currentState;
    private long remainingSeconds;
    private int sessionsCompleted = 0;
    private boolean isRunning = false;

    /* Default values */
    private int focusMinutes = 25;
    private int shortBreakMinutes = 5;
    private int longBreakMinutes = 15;
    private int sessionsTarget = 4;

    /* Constructor of Timer */
    public Timer() {
        this.currentState = State.STOPPED;
        this.remainingSeconds = focusMinutes * 60L;
    }

    /* Setter methods for the states durations */
    public void setFocusConfig(int minutes) { this.focusMinutes = minutes; }
    public void setShortBreakConfig(int minutes) { this.shortBreakMinutes = minutes; }
    public void setLongBreakConfig(int minutes) { this.longBreakMinutes = minutes; }
    public void setSessionsTarget(int count) { this.sessionsTarget = count; }

    /* Tick function to count time */
    public void tick() {
        if (!isRunning) return;

        if (remainingSeconds > 0) {
            remainingSeconds--;
        } else {
            nextState();
        }
    }

    /* Switching to the next state */
    public void nextState() {
        isRunning = false;

        switch (currentState) {
            case FOCUS:
                sessionsCompleted++;
                if (sessionsCompleted >= sessionsTarget) {
                    currentState = State.LONG_BREAK;
                    remainingSeconds = longBreakMinutes * 60L;
                    sessionsCompleted = 0; // Reiniciamos contador de sesiones
                } else {
                    currentState = State.SHORT_BREAK;
                    remainingSeconds = shortBreakMinutes * 60L;
                }
                break;

            case SHORT_BREAK:
            case LONG_BREAK:
            case STOPPED:
                currentState = State.FOCUS;
                remainingSeconds = focusMinutes * 60L;
                break;
        }
    }

    // Controls

    public void start() {
        if (currentState == State.STOPPED) {
            currentState = State.FOCUS;
            remainingSeconds = focusMinutes * 60L;
        }
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    public void skip() {
        nextState();
    }

    public void reset() {
        isRunning = false; // stops the timer

        switch (currentState) {
            case FOCUS:
                remainingSeconds = focusMinutes * 60L;
                break;
            case SHORT_BREAK:
                remainingSeconds = shortBreakMinutes * 60L;
                break;
            case LONG_BREAK:
                remainingSeconds = longBreakMinutes * 60L;
                break;
            default:
                remainingSeconds = focusMinutes * 60L; // Fallback
                break;
        }
    }

    /* Returns the remaining formated time of the timer */
    public String getFormattedTime() {
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public State getCurrentState() { return currentState; }
    public boolean isRunning() { return isRunning; }

    public int getSessionsTarget() {
        return sessionsTarget;
    }

    public int getSessionsCompleted() {
        return sessionsCompleted;
    }
}
