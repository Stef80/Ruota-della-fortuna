package rdFUtil.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.client.Client;
import serverRdF.Server;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Il controller della finestra per il reset della password. Inserendo la propria mail, il server mandera' una nuova password via email
 */

public class ForgottenPasswordController {
	@FXML
	private Button enterButton;
	@FXML
	private TextField mailText;
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
	 * @throws RemoteException In caso di errore di connesione al server
	 */

	public void enter() throws RemoteException {
		String mail = mailText.getText();
		boolean bool = server.resetPassword(client, mail);
		if(!bool){
			Notifications notification = Notifications.create()
					.title("Mail Notification")
					.text("L'indirizzo email non esiste.\nriprova!")
					.hideAfter(Duration.seconds(3))
					.position(Pos.CENTER);
			notification.showError();
		}else {
			Stage oldStage = (Stage) enterButton.getScene().getWindow();
			oldStage.close();
		}
	}
}
