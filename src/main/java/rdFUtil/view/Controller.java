package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rdFUtil.client.Client;
import serverRdF.Server;

import java.io.IOException;

public class Controller {
	@FXML
	private TextField emailText;
	@FXML
	private Button loginButton;
	@FXML
	private Button resetButton;
	@FXML
	private Button registerButton;
	private Server server;

	public Controller(){}

	public Controller(Server server, Client client) {
		this.server = server;
	}

	/**
	 * Legge le credenziali ed effettua il login aprendo la finestra principale.
	 *
	 * @throws IOException
	 */
	public void login() throws IOException {

		Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("tab_pane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		//	scene.getStylesheets().add(getClass().getResource("sampleScene.css").toExternalForm());
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage oldStage = (Stage) loginButton.getScene().getWindow();
		oldStage.hide();
	}

	public void register() throws IOException {
		Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registration_form_pane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("sampleScene.css").toExternalForm());
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage oldStage = (Stage) registerButton.getScene().getWindow();
		oldStage.close();

	}

	public void reset() throws IOException {
		Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("forgotten_password_pane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		//	scene.getStylesheets().add(getClass().getResource("sampleScene.css").toExternalForm());
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
