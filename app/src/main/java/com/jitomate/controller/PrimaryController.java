package com.jitomate.controller;
import com.jitomate.model.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class PrimaryController {
    // timer tab
    @FXML private Label displayTime;
    @FXML private Button btnStart;
    @FXML private  Button btnReset;
    @FXML private  Button btnSkip;

    // settings tab
    @FXML private TextField tfFocus;
    @FXML private TextField tfShortBreak;
    @FXML private TextField tfLongBreak;
    @FXML private TextField tfNumberOfSessions;

    // Timer object and animation
    private Timer timer;
    private Timeline timerAnimation;

    @FXML
    public void initialize() {
        // Timer object creation
        timer = new Timer();

        // setting default values
        tfFocus.setText("25");
        tfShortBreak.setText("5");
        tfLongBreak.setText("15");
        tfNumberOfSessions.setText("4");

        addConfigListeners();

        // update the ui with an animation for the timer
        timerAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            timer.tick();
            updateUI();
        }));
        timerAnimation.setCycleCount(Timeline.INDEFINITE);
        timerAnimation.play();

        updateUI();
    }

    private void addConfigListeners() {
        // Checks if the user changes the states values
        tfFocus.textProperty().addListener((obs, oldVal, newVal) -> {
            if (isNumeric(newVal)) timer.setFocusConfig(Integer.parseInt(newVal));
        });
        tfShortBreak.textProperty().addListener((obs, oldVal, newVal) -> {
            if (isNumeric(newVal)) timer.setShortBreakConfig(Integer.parseInt(newVal));
        });
        tfLongBreak.textProperty().addListener((obs, oldVal, newVal) -> {
            if (isNumeric(newVal)) timer.setLongBreakConfig(Integer.parseInt(newVal));
        });
        tfNumberOfSessions.textProperty().addListener((obs, oldVal, newVal) -> {
            if (isNumeric(newVal)) timer.setSessionsTarget(Integer.parseInt(newVal));
        });
    }

    /* Handles the start button */
    public void handleBtnStartAction() {
        if (timer.isRunning()) {
            timer.stop();
            btnStart.setText("Start");
        } else {
            timer.start();
            btnStart.setText("Stop");
        }
        updateUI();
    }

    /* Handles the reset button */
    public void handleBtnResetAction() {
        timer.reset();
        btnStart.setText("Start");
        updateUI();
    }

    /* Handles the skip button */
    public void handleBtnSkipAction() {
        timer.skip();
        btnStart.setText("Start");
        updateUI();
    }

    private void updateUI() {
        displayTime.setText(timer.getFormattedTime());

        // Visual feedback
        switch (timer.getCurrentState()) {
            case FOCUS -> displayTime.setStyle("-fx-text-fill: black;");
            case SHORT_BREAK -> displayTime.setStyle("-fx-text-fill: green;");
            case LONG_BREAK -> displayTime.setStyle("-fx-text-fill: blue;");
        }
    }

    /* Checks if the values are numeric*/
    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
