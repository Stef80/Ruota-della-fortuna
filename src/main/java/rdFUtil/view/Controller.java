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
import rdFUtil.logging.Login;
import serverRdF.Server;
import serverRdF.view.HostView;

import java.io.IOException;

public class Controller {
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
    private Server server;
    private Client client;
    private String titleFrame = "Wheel of Fortune";
    private static boolean admin;
    private static boolean isServer= false;

    public Controller(){}

    //Aggiunto il campo admin che dipende da dove viene avviato
    public Controller(Server server, Client client, boolean isAdmin) {
        this.server = server;
        this.client = client;
        admin = isAdmin;
    }

    /**
     * Legge le credenziali ed effettua il login aprendo la finestra principale.
     *
     * @throws IOException
     */
    public void login() throws IOException {
        String mail = emailTextField.getText();
        String password = passwordTextField.getText();
        Login login = new Login(password, mail);
        int result = server.signIn(login, client, admin);
        if(!isServer) {
            //int result = 0;
            if (result < 0) {
                Notifications notification = Notifications.create()
                                                     .title("Mail Notification")
                                                     .text("E-mail o password errati \nriprova!")
                                                     .hideAfter(Duration.seconds(3))
                                                     .position(Pos.CENTER);
                notification.showError();
            } else if (result == 0) {
                Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("tab_pane.fxml"));
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root);
                //   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setTitle(titleFrame);
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage oldStage = (Stage) loginButton.getScene().getWindow();
                oldStage.hide();
            } else {
                Notifications notification = Notifications.create()
                                                     .title("Mail Notification")
                                                     .text("Si sta provando ad accedere alla piattaforma dal client sbagliato \nriprova!")
                                                     .hideAfter(Duration.seconds(3))
                                                     .position(Pos.CENTER);
                notification.showError();
            }
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("host_view.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HostView hostname = loader.getController();
            hostname.takeAddress();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public void register() throws IOException {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registration_form_pane.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root);
       // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle(titleFrame);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Stage oldStage = (Stage) registerButton.getScene().getWindow();
        oldStage.close();

    }

    public void reset() throws IOException {
        Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("forgotten_password_pane.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root1);
       // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle(titleFrame);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setAdmin(boolean isAdmin){
        admin = isAdmin;
    }
    public static void setIsServer(boolean isS){
        isServer =isS;
    }
}
