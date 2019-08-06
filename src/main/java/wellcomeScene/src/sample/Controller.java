package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

	@FXML
	private Button resetButton;



	public void login(javafx.event.ActionEvent actionEvent) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("../firstPack/tabPane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Main.window.hide();
	}

	public void register(ActionEvent actionEvent) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("../PlayPack/registrationFormPanel.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Main.window.hide();
	}

	public void reset(ActionEvent event) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("/firstPack/forgottenPasswordPane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
