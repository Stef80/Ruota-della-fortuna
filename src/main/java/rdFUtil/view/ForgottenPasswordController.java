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

/**
 * Il controller della finestra per il reset della password inserendo la propria mail e inviando una nuova password
 */

public class ForgottenPasswordController {
	@FXML
	private Button enterButton;
	@FXML
	private TextField mailText;
	@FXML
	private Button backButton;
	private Server server;
	private Client client;

	public ForgottenPasswordController(){}

	public ForgottenPasswordController(Server server, Client client) {

		this.server = server;
		this.client = client;
	}

	/**
	 * il metodo gestisce il click del pulsante di invio, che acquisisce l'indirizzo mail inserito e automaticamente
	 * viene inviata una meil al proprio indirizzo con una nuova password
	 *
	 * @throws RemoteException
	 */

	public void enter() throws RemoteException {
		String mail = mailText.getText();
		server.resetPassword(client, mail);
		Stage oldStage = (Stage) enterButton.getScene().getWindow();
		oldStage.close();
	}

	/**
	 *
	 * @throws IOException
	 */

	public void back() throws IOException {
		Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource("OTP_registration_pane.fxml"));//TODO perchè va in otp e non in main?
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage thisStage = (Stage) backButton.getScene().getWindow();
		thisStage.close();
	}
}
