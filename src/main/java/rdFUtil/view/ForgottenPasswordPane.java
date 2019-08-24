package rdFUtil.view;

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
import java.rmi.RemoteException;

public class ForgottenPasswordPane {
	@FXML
	private Button enterButton;
	@FXML
	private TextField mailText;
	@FXML
	private Button backButton;
	private Server server;
	private Client client;

	public ForgottenPasswordPane(){}

	public ForgottenPasswordPane(Server server,Client client) {

		this.server = server;
		this.client = client;
	}

	public void enter() throws RemoteException {
		String mail = mailText.getText();
		server.resetPassword(client, mail);
		Stage oldStage = (Stage) enterButton.getScene().getWindow();
		oldStage.close();
	}

	public void back() throws IOException {
		Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource("OTP_registration_pane.fxml"));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage thisStage = (Stage) backButton.getScene().getWindow();
		thisStage.close();
	}
}
