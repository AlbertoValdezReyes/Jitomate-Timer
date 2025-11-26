package com.jitomate.controller;
import com.jitomate.model.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.event.EventHandler;

public class PrimaryController {
    @FXML
    private Label displayTime;

    @FXML
    private Button btnStart;

    @FXML
    private  Button btnStop;

    @FXML
    private  Button btnSkip;

    @FXML
    private TextField tfFocus;
    @FXML
    private TextField tfShortBreak;
    @FXML
    private TextField tfLongBreak;
    @FXML
    private TextField tfNumberOfSessions;

    private Timer timer;
    private Timeline timerAnimation;

    @FXML
    public void initialize() {
        /* Set the default time to 60 minutes */
        tfFocus.setText("60");

        EventHandler<ActionEvent> updateTimer = e -> {
            displayTime.setText(timer.getRemainingFormatedTime());
        };

        timerAnimation = new Timeline(
                new KeyFrame(Duration.millis(1000), updateTimer)
        );
        timerAnimation.setCycleCount(Timeline.INDEFINITE);
    }

    public void handleBtnStartAction() {
        timer = new Timer(Integer.parseInt(tfFocus.getText()));
        displayTime.setText(timer.getRemainingFormatedTime());
        timer.start();
        timerAnimation.play();
    }

    public void handleBtnStopAction() {
        timer.stop();
        timerAnimation.stop();
    }

    public void handleBtnSkipAction() {
        if (timer != null) {
            timer.stop();
            timerAnimation.stop();
            displayTime.setText("00:00:00");
            timer = null;
        }
    }
}
