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
import rdFUtil.client.AdminChecker;
import rdFUtil.client.Client;
import rdFUtil.logging.Login;
import serverRdF.Server;
import serverRdF.view.HostViewController;
import serverRdF.view.InsubriaLoginController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Il controller della finestra di login. Da qui e' possibile inserire le credenziali del proprio account e accedere alle funzionalita' della piattaforma,
 * registrare un nuovo account oppure resettare la propria password.
 */
public class Controller implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button registerButton;
    private static Server server;
    private static Client client;
    private static boolean admin;
    private static boolean isServer = false;

    public Controller() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!InsubriaLoginController.gogo)
            WelcomePane.setController(this);
        else
            InsubriaLoginController.setController(this);
    }

    /**
     * Legge le credenziali ed effettua il login aprendo la finestra principale.
     * se il login avviene dal server mostra l'hostname del server che deve essere utilizzato
     * per la connessione.
     *
     * @throws IOException Nel caso in cui non sia possibile accedere alla finestra successiva
     */
    public void login() throws Exception {
        String mail = emailTextField.getText();
        String password = passwordTextField.getText();
        if (!(mail.equals("") || password.equals(""))) {
            Login login = new Login(password, mail);
            int result = server.signIn(login, client, admin);
            //int result = 0;
            if (result < 0) {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("E-mail o password errati \nriprova!")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BASELINE_RIGHT);
                notification.showError();
            } else if (result == 0) {
                //     FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("tab_pane.fxml"));
                if (!isServer) {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("tab_pane.fxml"));
//                Parent root = null;
//                try {
//                    root = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                TabPane tabPane = loader.getController();
//                tabPane.setGlobalStats();
//                tabPane.setUserStat();
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root);
                    primaryStage.setTitle(FrameTitle.main);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    Stage oldStage = (Stage) loginButton.getScene().getWindow();
                    oldStage.hide();
                } else {
                    FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("host_view.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    Stage oldStage = (Stage) loginButton.getScene().getWindow();
                    oldStage.hide();
                }
            } else {
                Notifications notification = Notifications.create()
                        .title("Mail Notification")
                        .text("Si sta provando ad accedere alla piattaforma dal client sbagliato \nriprova!")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BASELINE_RIGHT);
                notification.showError();
            }
        }
    }

    /**
     * Apre la finestra utilizzata per la registrazione di un nuovo utente
     *
     * @throws IOException In caso non sia possibile aprire la nuova finestra
     */
    public void register() throws IOException {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registration_form_pane.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Stage oldStage = (Stage) registerButton.getScene().getWindow();
        oldStage.close();

    }

    /**
     * Apre la finestra utilizzata per il reset della password
     *
     * @throws IOException In caso non sia possibile aprire la finestra
     */
    public void reset() throws IOException {
        Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("forgotten_password_pane.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root1);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void setIsServer(boolean isS){
        isServer =isS;
    }

    /**
     * Metodo utilizzato per passare informazioni del client a {@link TabPaneController}
     *
     * @param tb il riferimento al controller {@link TabPaneController}
     */
    public static void setArgs(TabPaneController tb){
        tb.setClient(client);
        tb.setServer(server);
        tb.setAdmin(admin);
    }

    public static void setRegistration(RegistrationFormController registration){
        registration.setClient(client);
        registration.setServer(server);
        registration.setServer(false);
        registration.setAdmin(AdminChecker.isIsAdmin());
    }

    public static void setResetPanel(ForgottenPasswordController f){
        f.setClient(client);
        f.setServer(server);
    }

    public static void setServer(Server server) {
        Controller.server = server;
    }

    public static void setClient(Client client) {
        Controller.client = client;
    }

    public static void setAdmin(boolean admin) {
        Controller.admin = admin;
    }
}
