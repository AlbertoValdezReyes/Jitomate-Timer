package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

public class Pomodoro extends Application {
	@Override
	public void start(Stage primaryStage) {
		//Create a Vbox to hold the timer 
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(20);

		//Create a Text for the timer
		Text timer = new Text("TIMER");
		vbox.getChildren().add(timer);
		//Create a start button
		Button startButton = new Button("Start");
		vbox.getChildren().add(startButton);

		Scene scene = new Scene(vbox, 300, 300);
		primaryStage.setTitle("Pomodoro Timer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
