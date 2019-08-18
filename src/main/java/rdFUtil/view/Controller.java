package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.client.Client;
import rdFUtil.logging.Login;
import serverRdF.Server;

import java.io.IOException;

public class Controller {
	@FXML
	private TextField emailText;
	@FXML
	private PasswordField passwordText;
	@FXML
	private Button loginButton;
	@FXML
	private Button resetButton;
	@FXML
	private Button registerButton;
	private Server server;
	private Client client;

	public Controller(Server server, Client client) {
		this.server = server;
		this.client = client;
	}

	/**
	 * Legge le credenziali ed effettua il login aprendo la finestra principale.
	 *
	 * @throws IOException
	 */
	public void login() throws IOException {
		String mail = emailText.getText();
		String password = passwordText.getText();
		Login login = new Login(password,mail);
		if(!server.checkEMail(mail) && server.checkPassword(password)){
			Notifications notification = Notifications.create()
												 .title("Mail Notification")
												 .text("E-mai o password errati \nriprova!")
												 .hideAfter(Duration.seconds(3))
												 .position(Pos.CENTER);
			notification.showError();
		}else{
			server.signIn(login,client);

		Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("tab_pane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage oldStage = (Stage) loginButton.getScene().getWindow();
		oldStage.hide();
	    }
	}

	public void register() throws IOException {
		Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registration_form_pane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage oldStage = (Stage) registerButton.getScene().getWindow();
		oldStage.close();

	}

	public void reset() throws IOException {
		Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("forgotten_password_pane.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
