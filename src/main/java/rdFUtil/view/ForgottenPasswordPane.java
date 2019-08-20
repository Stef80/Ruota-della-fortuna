package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rdFUtil.client.Client;
import serverRdF.Server;

import java.rmi.RemoteException;

<<<<<<< HEAD
	public ForgottenPasswordPane(Server server,Client client) {
=======
public class ForgottenPasswordPane {
	@FXML
	private Button enterButton;
	@FXML
	private TextField mailText;
	private Server server;
	private Client client;

	public ForgottenPasswordPane(Server server, Client client) {
>>>>>>> parent of d8b696f... aggiunta metodi senza argomenti

		this.server = server;
		this.client = client;
	}

	public void enter() throws RemoteException {
		String mail = mailText.getText();
		server.resetPassword(client, mail);
		Stage oldStage = (Stage) enterButton.getScene().getWindow();
		oldStage.close();


	}
}
