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
import rdFUtil.view.Controller;
import serverRdF.dbComm.DBManager;
import serverRdF.emailRdF.EmailManager;

import java.io.IOException;

public class InsubriaLoginPane {
	@FXML
	private TextField userTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button confirmButton;
	private EmailManager emailManager;
	private static DBManager dbManager;


	public void loginManager() throws IOException {
		String user = userTextField.getText();
		String password = passwordTextField.getText();
		boolean logged = EmailManager.logIntoAccount(user,password);
		if(logged){
		EmailManager emailManager = EmailManager.createEmailManager(user,password);
		if(dbManager.getAnyAdmin()){
			Controller.setAdmin(true);
			Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root1);
			//   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			primaryStage.setTitle("");
			primaryStage.setScene(scene);
			primaryStage.show();
			Stage oldStage = (Stage) confirmButton.getScene().getWindow();
			oldStage.hide();
		}
		}else{
			Notifications notification = Notifications.create()
												 .title("Mail Notification")
												 .text("E-mail o password errati \nimmettere nuova mail")
												 .hideAfter(Duration.seconds(3))
												 .position(Pos.CENTER);
			notification.showError();
		}
	}

	public static void setDbManager(DBManager db){
		dbManager = db;
	}
}
