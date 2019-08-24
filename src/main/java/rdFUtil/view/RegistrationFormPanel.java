package rdFUtil.view;

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
import rdFUtil.logging.User;
import serverRdF.Server;
import serverRdF.registrationRdF.OTPHelper;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class RegistrationFormPanel {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    Button confirmButton;
    @FXML
    private PasswordField password;
    @FXML
    private Button backButton;
    private Server server;
    private Client client;
    private User user;
    private boolean admin;

    public RegistrationFormPanel(){}

    public RegistrationFormPanel(Server server, Client client, boolean admin) {
        this.server = server;
        this.client = client;
        this.admin = admin;
    }

    /**
     * Registra il nuovo user verificando che non esista già tramite il confronto
     * tra la mail inserita e quelle già registrate se non esiste la registra
     * visualizza una finestra di errore altrimenti
     *
     * @throws IOException
     */
    public void confirm() throws IOException {
        //se la mail non esiste visualizza notifica
        if (!(nameTextField.getText().equals("") || surnameTextField.getText().equals("") || nicknameTextField.getText().equals("") || emailTextField.getText().equals("") || password.getText().equals(""))) {
            if (!server.checkEMail(emailTextField.getText())) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("E-mail già presente \nimmettere nuova mail")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER);
                notification.showError();
                //se esiste nickName visualizza notifica
            } else if (!server.checkNickname(nicknameTextField.getText())) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("NickName già presente \nimmettere un nuovo nickname")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER);
                notification.showError();

            } else {
                String nameStr = nameTextField.getText();
                String surnameStr = surnameTextField.getText();
                String nickStr = nicknameTextField.getText();
                String mailStr = emailTextField.getText();
                String passwordStr = password.getText();
                user = new User(passwordStr, mailStr, nameStr, surnameStr, nickStr);
                OTPHelper otpHelper = server.signUp(user, client, true);//todo modificare l'import di OTPHelper
                new OTPRegistrationPane(server, client, otpHelper);
                Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource("OTP_registration_pane.fxml"));
                Scene scene = new Scene(root);
                Stage primaryStage = new Stage();
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setTitle("Wheel of Fortune");
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage thisStage = (Stage) confirmButton.getScene().getWindow();
                thisStage.close();
            }
        } else {
            Notifications notification = Notifications.create()
                    .title("Registration Notification")
                    .text("Errore:\nTutti i campi sono obbligatori")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER);
            notification.showError();
        }
    }

    public void back() throws IOException{
        Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource("OTP_registration_pane.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Wheel of Fortune");
        primaryStage.setScene(scene);
        primaryStage.show();
        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
    }
}
