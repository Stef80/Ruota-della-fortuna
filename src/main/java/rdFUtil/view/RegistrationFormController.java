package rdFUtil.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import serverRdF.view.InsubriaLoginController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Il controller della finestra di registrazione. Permette di compilare tutti i campi necessari e registrare un nuovo utente. Grazie al campo 'admin'
 * {@link RegistrationFormController} e' in grado di chiedere al server sia la registrazione di un admin che di un giocatore
 */
public class RegistrationFormController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    Button confirmButton;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button backButton;
    private static Server server;
    private static Client client;
    private User user;
    private boolean admin;
    private static boolean isServer;
    private static OTPHelper otp;

    public RegistrationFormController(){}

    /**
     * Registra il nuovo user verificando che non esista già tramite il confronto
     * tra la mail inserita e quelle già registrate se non esiste la registra
     * visualizza una finestra di errore altrimenti
     *
     * @throws IOException In caso non sia possibile accedere alla finestra successiva
     */
    public void confirm() throws IOException {
        //se la mail non esiste visualizza notifica
        System.out.println("dati: "+server==null? "no" : "ok" + " ");
        if (!(nameTextField.getText().equals("") || surnameTextField.getText().equals("") || nicknameTextField.getText().equals("") || mailTextField.getText().equals("") || passwordTextField.getText().equals(""))) {
            if (!server.checkEMail(mailTextField.getText())) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("E-mail già presente \nimmettere nuova mail")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BASELINE_RIGHT);
                notification.showError();
                //se esiste nickName visualizza notifica
            } else if (!server.checkNickname(nicknameTextField.getText())) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("NickName già presente \nimmettere un nuovo nickname")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BASELINE_RIGHT);
                notification.showError();

            } else {
                String nameStr = nameTextField.getText();
                String surnameStr = surnameTextField.getText();
                String nickStr = nicknameTextField.getText();
                String mailStr = mailTextField.getText();
                String passwordStr = passwordTextField.getText();
                user = new User(passwordStr, mailStr, nameStr, surnameStr, nickStr);
                otp = server.signUp(user, client, true);//todo modificare l'import di OTPHelper
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("OTP_registration_pane.fxml"));
                Scene scene = new Scene(root);
                Stage primaryStage = new Stage();
                //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setTitle(FrameTitle.main);
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
                    .position(Pos.BASELINE_RIGHT);
            notification.showError();
        }
    }

    /**
     * Permette di tornare alla schermata di login
     *
     * @throws IOException In caso non sia possibile accedere alla finestra
     */
    public void back() throws IOException{
        if(!isServer) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main_pane.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage thisStage = (Stage) backButton.getScene().getWindow();
            thisStage.close();
        }else{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("insubria_login_pane.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage thisStage = (Stage) backButton.getScene().getWindow();
            thisStage.close();
        }
    }

    public Button getBackButton() {
        return backButton;
    }

    public static void setServer(boolean server) {
        isServer = server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(InsubriaLoginController.gogo) {
            InsubriaLoginController.setReg(this);
        }else {
            Controller.setRegistration(this);
        }
    }

    public static void setOTP(OTPRegistrationController otpp){
        otpp.setClient(client);
        otpp.setServer(server);
        otpp.setOtp(otp);
    }
}
