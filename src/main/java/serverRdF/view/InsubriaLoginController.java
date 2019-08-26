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
import rdFUtil.client.ClientImplementation;
import rdFUtil.view.Controller;
import rdFUtil.view.ForgottenPasswordController;
import rdFUtil.view.RegistrationFormController;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;
import serverRdF.emailRdF.EmailManager;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Controller della finestra utilizzata per effettuare l'accesso ad un account Insubria per l'invio delle mail automatiche.
 */
public class InsubriaLoginController {
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button confirmButton;
    private EmailManager emailManager;
    private static DBManager dbManager;
    private static Registry registry;
    private static ServerImplementation server;


    /**
     * Questo metodo cerca di stabilire la connessione all'account Insubria attraverso l'invio di una email. Se l'invio avviene senza problemi,
     * viene ricercata nel databse la presenza di admin. Se sono presenti degli admin, viene aperta la schermata di accesso, altrimenti viene aperta la schermata di registrazione
     *
     * @throws IOException In caso non riesca a caricare la finestra successiva
     * @throws RemoteException
     */
    public void loginManager() throws IOException, RemoteException {
        String user = userTextField.getText();
        String password = passwordTextField.getText();
        boolean logged = EmailManager.logIntoAccount(user, password);
        if (logged) {
            emailManager = EmailManager.createEmailManager(user, password);
            server = new ServerImplementation(dbManager, emailManager);
            if ((registry = LocateRegistry.getRegistry(1099)) == null) {
                registry = LocateRegistry.createRegistry(1099);
            }
            ClientImplementation client = new ClientImplementation();
            new Controller(server, client, true);
            Controller.setIsServer(true);
            new ForgottenPasswordController(server, client);
            new RegistrationFormController(server, client, true, true);
            if (dbManager.getAnyAdmin()) {
                Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root1);
                //   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setTitle("");
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage oldStage = (Stage) confirmButton.getScene().getWindow();
                oldStage.hide();
            } else {
                Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registration_form_pane"));
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root1);
                //   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setTitle("");
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage oldStage = (Stage) confirmButton.getScene().getWindow();
                oldStage.hide();
            }
        } else {
            Notifications notification = Notifications.create()
                    .title("Mail Notification")
                    .text("E-mail o password errati \nimmettere nuova mail")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER);
            notification.showError();
        }
    }

    public static void setDbManager(DBManager db) {
        dbManager = db;
    }

    public static ServerImplementation getServer() {
        return server;
    }

    public static Registry getRegistry() {
        return registry;
    }
}
