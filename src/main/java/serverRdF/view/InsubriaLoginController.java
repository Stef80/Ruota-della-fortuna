package serverRdF.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rdFUtil.ApplicationCloser;
import rdFUtil.Notification;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;
import rdFUtil.view.Controller;
import rdFUtil.view.FrameTitle;
import rdFUtil.view.RegistrationFormController;
import serverRdF.Server;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;
import serverRdF.emailRdF.EmailManager;

import java.io.IOException;
import java.rmi.RemoteException;
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
    private static Server server;
    private static Client client;
    public static boolean forServer = false;


    /**
     * Questo metodo cerca di stabilire la connessione all'account Insubria attraverso l'invio di una email. Se l'invio avviene senza problemi,
     * viene ricercata nel database la presenza di admin. Se sono presenti degli admin, viene aperta la schermata di accesso, altrimenti viene aperta la schermata di registrazione
     *
     * @throws IOException     In caso non riesca a caricare la finestra successiva
     * @throws RemoteException
     */
    public void loginManager() throws IOException, RemoteException {
        String user = userTextField.getText();
        String password = passwordTextField.getText();
        boolean logged = EmailManager.logIntoAccount(user, password);
        if (logged) {
            forServer = true;
            emailManager = EmailManager.createEmailManager(user, password);
            server = new ServerImplementation(dbManager, emailManager);
//            System.out.println("Server creato");
            client = new ClientImplementation();
            if (dbManager.getAnyAdmin()) {
                Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root1);
                primaryStage.setTitle(FrameTitle.main);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setOnCloseRequest((WindowEvent event1) -> {
                    System.exit(0);
                });
                Stage oldStage = (Stage) confirmButton.getScene().getWindow();
                oldStage.hide();
            } else {
                Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("registration_form_pane.fxml"));
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root1);
                primaryStage.setTitle(FrameTitle.main);
                primaryStage.setScene(scene);
                primaryStage.show();
                ApplicationCloser.setCloser(primaryStage);
                Stage oldStage = (Stage) confirmButton.getScene().getWindow();
                oldStage.close();

            }
        } else {
            Notification.notify("Mail Notification", "E-mail o password errati \nimmettere nuova mail", true);
        }
    }

    public static void setDbManager(DBManager db) {
        dbManager = db;
    }

    public static Server getServer() {
        return server;
    }

    public static Registry getRegistry() {
        return registry;
    }

    public static void setReg(RegistrationFormController r) {
        r.setServer(true);
        r.setServer(server);
        r.setAdmin(true);
        r.setClient(client);
    }

    public static void setController(Controller ctr) {
        ctr.setClient(client);
        ctr.setServer(server);
        ctr.setAdmin(true);
        ctr.setIsServer(true);
    }

    public static void setHost(HostViewController host) {
        host.setServer(server);
        host.setR(registry);
    }
}
