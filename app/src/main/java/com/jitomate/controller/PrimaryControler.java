package com.jitomate.controller;
import com.jitomate.model.Timer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrimaryControler {
    private Timer timer = new Timer(50);
    @FXML
    private Label displayTime;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private  Button btnReset;

    public void handleBtnStartAction () {
        timer.start();

        new Thread(() -> {
            try {
                while(timer.isRunning()) {
                    Platform.runLater(() -> {
                        displayTime.setText(timer.displayCurrentTimer());
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
            }
        }).start();
    }

    public void handleBtnStopAction() {
        timer.stop();
    }
}
