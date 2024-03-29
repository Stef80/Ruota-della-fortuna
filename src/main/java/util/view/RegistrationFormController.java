package util.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ApplicationCloser;
import util.Notification;
import util.client.Client;
import util.logging.User;
import server.Server;
import server.email.EmailAddressDoesNotExistException;
import server.registration.OTPHelper;
import server.view.InsubriaLoginController;

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

    public RegistrationFormController() {
    }

    /**
     * Registra il nuovo user verificando che non esista già tramite il confronto
     * tra la mail inserita e quelle già registrate, se non esiste la registra,
     * visualizza una finestra di errore altrimenti
     *
     * @throws IOException In caso non sia possibile accedere alla finestra successiva
     */
    public void confirm() throws IOException {
        //se la mail non esiste visualizza notifica
        if (!(nameTextField.getText().equals("") || surnameTextField.getText().equals("") || nicknameTextField.getText().equals("") || mailTextField.getText().equals("") || passwordTextField.getText().equals(""))) {
            if (!server.checkEMail(mailTextField.getText())) {
                Notification.notify("Mail Notification", "E-mail già presente \nimmettere nuova mail", true);
                //se esiste nickName visualizza notifica
            } else if (!server.checkNickname(nicknameTextField.getText())) {
                Notification.notify("Mail Notification", "Nickname già presente \nimmettere un nuovo nickname", true);
            } else {
                String nameStr = nameTextField.getText();
                String surnameStr = surnameTextField.getText();
                String nickStr = nicknameTextField.getText();
                String mailStr = mailTextField.getText();
                String passwordStr = passwordTextField.getText();
//                user = new User(passwordStr, buildString(mailStr), buildString(nameStr), buildString(surnameStr), buildString(nickStr));
                user = new User(passwordStr, mailStr, nameStr, surnameStr, nickStr);
                try {
                    otp = server.signUp(user, client, admin);
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("OTP_registration_pane.fxml"));
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle(FrameTitle.main);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    ApplicationCloser.setCloser(primaryStage);
                    Stage thisStage = (Stage) confirmButton.getScene().getWindow();
                    thisStage.close();
                } catch (EmailAddressDoesNotExistException e) {
                    this.notifyIllegalEmailAddress();
                }
            }
        } else {
            Notification.notify("Registration Notification", "Errore:\nTutti i campi sono obbligatori", true);
        }
    }

    /**
     * Permette di tornare alla schermata di login
     *
     * @throws IOException In caso non sia possibile accedere alla finestra
     */
    public void back() throws IOException {
        if (!isServer) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main_pane.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            ApplicationCloser.setCloser(primaryStage);
            Stage thisStage = (Stage) backButton.getScene().getWindow();
            thisStage.close();
        } else {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("insubria_login_pane.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            ApplicationCloser.setCloser(primaryStage);
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
        if (InsubriaLoginController.forServer) {
            InsubriaLoginController.setReg(this);
        } else {
            Controller.setRegistration(this);
        }
    }

    /**
     * Metodo utilizzato per passare le informazioni del client a {@link OTPRegistrationController}
     *
     * @param otpp il riferimento al controller {@link OTPRegistrationController}
     */
    public static void setOTP(OTPRegistrationController otpp) {
        otpp.setClient(client);
        otpp.setServer(server);
        otpp.setOtp(otp);
    }

    /**
     * Notifica che non e' stato possibile inviare la mail all'indirizzo email specificato al momento della registrazione
     * per problemi di connessione o perche' non esistente
     */
    public void notifyIllegalEmailAddress() {
        Notification.notify("Errore", "L'indirizzo email inserito non\nè disponibile o non esiste.", true);
    }

//    private String buildString(String s){
//        String result = "";
//        char c;
//        for(int i=0; i<s.length(); i++){
//            c = s.charAt(i);
//            result += c;
//            if(c == '\''){
//                result += "'";
//            }
//        }
//        return result;
//    }
}
