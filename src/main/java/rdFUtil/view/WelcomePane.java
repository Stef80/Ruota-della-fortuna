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
import rdFUtil.client.AdminChecker;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;
import serverRdF.Server;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Controller della finestra iniziale di PlayerRdF e AdminRdF. Inserendo l'hostname del server sara' possibile creare una connessione
 */
public class WelcomePane {
	@FXML
	private TextField hostnameTextField;
	@FXML
	private Button confirmButton;

	private Registry registry;
	private Server server;
	private Client client;


	/**
	 * Questo metodo controlla l'hostname inserito e prova a stabilire una connessione. In caso di riuscita verra' caricata la schermata di login, in caso
	 * contrario viene segnalato l'errore
	 */
	public void startGameView(){
		String host = hostnameTextField.getText();
		try {
			registry = LocateRegistry.getRegistry(host,1099);
			server = (Server) registry.lookup("SERVER");
			client = new ClientImplementation();
			new Controller(server,client, AdminChecker.isIsAdmin());
			new ForgottenPasswordController(server,client);
            GamePlayerController game = new GamePlayerController(client);
            new RegistrationFormController(server,client,AdminChecker.isIsAdmin(),false);
			client.setGameController(game);
			Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root1);
			primaryStage.setTitle("Wheel of Fortune");
			primaryStage.setScene(scene);
			primaryStage.show();
			Stage oldStage = (Stage) confirmButton.getScene().getWindow();
			oldStage.close();


		}catch (RemoteException | NotBoundException e){
			Notifications notification = Notifications.create()
												 .title("Connection Notification")
												 .text("Connessione non riuscita \nriprovare")
												 .hideAfter(Duration.seconds(3))
												 .position(Pos.CENTER);
			notification.showError();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
