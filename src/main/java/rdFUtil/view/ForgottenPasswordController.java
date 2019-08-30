package rdFUtil.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.Notification;
import rdFUtil.client.Client;
import serverRdF.Server;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Il controller della finestra per il reset della password. Inserendo la propria mail, il server mandera' una nuova password via email
 */

public class ForgottenPasswordController  implements Initializable {
	@FXML
	private Button enterButton;
	@FXML
	private TextField mailText;
	private Server server;
	private Client client;

	public ForgottenPasswordController(){}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Controller.setResetPanel(this);
	}

	/**
	 * il metodo gestisce il click del pulsante di invio, che acquisisce l'indirizzo mail inserito e automaticamente
	 * viene inviata una mail al proprio indirizzo con una nuova password
	 *
	 * @throws RemoteException In caso di errore di connesione al server
	 */

	public void enter() throws RemoteException {
		String mail = mailText.getText();
		boolean bool = server.resetPassword(client, mail);
		if(!bool){
			Notification.notification("Mail Notification", "L'indirizzo email non esiste.\nriprova!", 3, true);
		}else {
			Notification.notification("Successo", "E' stata inviata una mail contenente la nuova password", 3, false);
			Stage oldStage = (Stage) enterButton.getScene().getWindow();
			oldStage.close();
		}
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
