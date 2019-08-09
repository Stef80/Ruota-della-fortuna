package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
	@FXML
	private TextField emailString;
	@FXML
    private Button loginButton;
	@FXML
	private Button resetButton;
	@FXML
	private Button registerButton;



	public void login(ActionEvent actionEvent) throws IOException {


		Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("tabPane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage oldStage = (Stage) loginButton.getScene().getWindow();
		oldStage.close();
	}

	public void register(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registrationFormPanel.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage oldStage = (Stage) registerButton.getScene().getWindow();
		oldStage.close();

	}

	public void reset(ActionEvent event) throws IOException {
		Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("forgottenPasswordPane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
