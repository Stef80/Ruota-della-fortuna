package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.registrationRdF.OTPHelper;
import serverRdF.registrationRdF.WaitingThread;

import java.io.IOException;
import java.rmi.RemoteException;

public class OTPRegistrationPane {
	@FXML
    private TextField otpTextField;
	@FXML
	private Button confirmButton;
	private Server server;
	private Client client;
	private OTPHelper otp;

	public OTPRegistrationPane(Server server, Client client,OTPHelper otp){
		this.server = server;
		this.client = client;
		this.otp = otp;
	}

	public void enter() throws IOException {
		String otpStr = otpTextField.getText();
		try {
			otp.checkOTP(otpStr,client);
		} catch (RemoteException e) {

		}
		Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource("main_pane.fxml"));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage thisStage = (Stage) confirmButton.getScene().getWindow();
		thisStage.close();
	}
}
