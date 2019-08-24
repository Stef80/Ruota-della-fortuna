package serverRdF.view;

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
import serverRdF.dbComm.DBManager;

import java.io.IOException;
import java.sql.SQLException;


public class ServerMainPane {
    @FXML
	private TextField userTextField;
    @FXML
	private PasswordField passwordTextField;
    @FXML
	private TextField hostnameTextField;
    @FXML
	private Button confirmButton;
     private DBManager manager;


    public void login() throws IOException {

    	String user = userTextField.getText();
    	String password = passwordTextField.getText();
    	String hostname = hostnameTextField.getText();
		try {
			manager = DBManager.createDBManager(hostname,user,password);
			InsubriaLoginPane.setDbManager(manager);
			Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("insubria_login_pane.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root1);
			//   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			primaryStage.setTitle("");
			primaryStage.setScene(scene);
			primaryStage.show();
			Stage oldStage = (Stage) confirmButton.getScene().getWindow();
			oldStage.hide();

		} catch (SQLException e) {
			Notifications notification = Notifications.create()
												 .title("Connection Notification")
												 .text("Connessione non riuscita \nriprovare")
												 .hideAfter(Duration.seconds(3))
												 .position(Pos.CENTER);
			notification.showError();
		}

	}
}