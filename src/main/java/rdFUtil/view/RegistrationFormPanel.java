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

import java.io.IOException;

public class RegistrationFormPanel {

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField nickname;
    @FXML
    TextField eMail;
    @FXML
    Button confirmButton;
    @FXML
    private PasswordField password;
    private Server server;
    private Client client;
    private User user;

    public RegistrationFormPanel(Server server, Client client) {
        this.server = server;
        this.client = client;
    }

    /**
     * Registra il nuovo user verificando che non esista già tramite il confronto
     * tra la mail inserita e quelle già registrate se non esiste la registra
     * visualizza unafinestra di errore altrimenti
     *
     * @throws IOException
     */
    public void confirm() throws IOException {
        //se la mail non esiste visualizza notifica
        if (!(name.getText().equals("") || surname.getText().equals("") || nickname.getText().equals("") || eMail.getText().equals("") || password.getText().equals(""))) {
            if (!server.checkEMail(eMail.getText())) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("E-mail già presente \nimmettere nuova mail")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER);
                notification.showError();
                //se esiste nickName visualizza notifica
            } else if (!server.checkNickname(nickname.getText())) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("NickName già presente \nimmettere un nuovo nickname")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER);
                notification.showError();

            } else {
                String nameStr = name.getText();
                String surnameStr = surname.getText();
                String nickStr = nickname.getText();
                String mailStr = eMail.getText();
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
}
