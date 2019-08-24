package adminRdF;

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
import rdFUtil.client.ClientImplementation;
import rdFUtil.view.*;
import serverRdF.Server;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WellcomePane {
	@FXML
	private TextField hostnameTextField;
	@FXML
	private Button confirmButton;

	private Registry registry;
	private Server server;
	private Client client;


	public void startGameView(){
		String host = hostnameTextField.getText();
		try {
			registry = LocateRegistry.getRegistry(host,1099);
			server = (Server) registry.lookup("SERVER");
			client = new ClientImplementation();
			new Controller(server,client,PrimePane.ISADMIN);
			new ForgottenPasswordPane(server,client);
            new GamePlayerController(client);
            new RegistrationFormPanel(server,client,PrimePane.ISADMIN);
            new TabPane(server,client);
			Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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