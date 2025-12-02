package com.jitomate.controller;
import com.jitomate.model.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class PrimaryController {
    // timer tab
    @FXML private Label displayTime;
    @FXML private HBox sessionsContainer;
    @FXML private Label lblSessionState;
    @FXML private Button btnStart;
    @FXML private Button btnReset;
    @FXML private Button btnSkip;

    // settings tab
    @FXML private TextField tfFocus;
    @FXML private TextField tfShortBreak;
    @FXML private TextField tfLongBreak;
    @FXML private TextField tfNumberOfSessions;

    // logic
    private Timer timer;
    private Timeline timerAnimation;
    private List<Circle> sessionDots = new ArrayList<>();

    @FXML
    public void initialize() {
        // Timer object creation
        timer = new Timer();

        // setting default values
        tfFocus.setText("25");
        tfShortBreak.setText("5");
        tfLongBreak.setText("15");
        tfNumberOfSessions.setText("4");

        initSessionDots();

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

    private void initSessionDots() {
        sessionsContainer.getChildren().clear();
        sessionDots.clear();

        int total = timer.getSessionsTarget();

        for (int i = 0; i < total; i++) {
            Circle dot = new Circle(6); // Radio 6
            dot.setStroke(Color.GRAY);
            dot.setFill(Color.TRANSPARENT);

            sessionsContainer.getChildren().add(dot);
            sessionDots.add(dot);
        }

        updateSessionDotsVisuals();
    }

    private void addConfigListeners() {
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
            if (isNumeric(newVal)) {
                timer.setSessionsTarget(Integer.parseInt(newVal));
                initSessionDots(); // Rebuild dots
            }
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
        lblSessionState.setText(timer.getCurrentStateString());
        updateSessionDotsVisuals();
    }

    private void updateSessionDotsVisuals() {
        if (sessionDots.size() != timer.getSessionsTarget()) {
            initSessionDots();
            return;
        }

        int current = timer.getSessionsCompleted();

        for (int i = 0; i < sessionDots.size(); i++) {
            Circle dot = sessionDots.get(i);

            if (i < current) {
                // Past
                if (dot.getFill() != Color.web("#3d3b3b")) {
                    dot.setFill(Color.web("#3d3b3b"));
                }
            } else if (i == current) {
                // Present
                if (timer.getCurrentState() == Timer.State.FOCUS) {
                    dot.setFill(Color.RED);
                } else {
                    dot.setFill(Color.web("#3d3b3b"));
                }
            } else {
                // Future
                dot.setFill(Color.TRANSPARENT);
            }
        }
    }

    /* Checks if the values are numeric*/
    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
