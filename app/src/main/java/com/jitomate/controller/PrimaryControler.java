package com.jitomate.controller;
import com.jitomate.model.Timer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryControler {
    @FXML
    private Label displayTime;

    @FXML
    private Button btnStart;

    @FXML
    private  Button btnStop;

    @FXML
    private  Button btnSkip;

    @FXML
    private TextField lblMinutes;

    private Timer timer;

    @FXML
    public void initialize() {
        timer = new Timer(Integer.parseInt(lblMinutes.getText()));
    }

    private void updateTimer() {
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

    boolean clicked = false;
    public void handleBtnStartAction() {

        if(timer.isRunning() && clicked) {
            timer.setRunning(false);
            btnStart.setText("Start");
            updateTimer();
        }
        else if(!timer.isRunning() && clicked) {
           timer.setRunning(true);
           btnStart.setText("Pause");
           updateTimer();
        }
        else if(!timer.isRunning() && !clicked) {
            timer.start();
            updateTimer();
            btnStart.setText("Pause");
            clicked = true;
        }



    }

    public void handleBtnStopAction() {
        timer.start();
        updateTimer();
    }

    public void handleBtnSkipAction() {

    }

}
