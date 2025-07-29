package com.jitomate;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		URL fxmlURL = App.class.getResource("views/primary.fxml");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(fxmlURL);
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Jitomate-Timer");
		stage.show();
	}
}
