package server.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.Notification;
import util.view.FrameTitle;
import server.dbComm.DBManager;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Il controller della prima finestra visibile all'avvio del server. Permette di accedere al database inserendo l'url del database e le credenziali d'accesso
 */
public class ServerMainPane {
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField hostnameTextField;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField portTextField;
    private DBManager manager;


    /**
     * Cerca di collegarsi al database con le credenziali fornite dall'utente. Se si riesce a stabilire la connessione si passa alla schermata successiva,
     * controllata da {@link InsubriaLoginController}
     *
     * @throws IOException In caso non sia possibile caricare la schermata successiva
     */
    public void login() throws IOException {

        String user = userTextField.getText();
        String password = passwordTextField.getText();
        String hostname = hostnameTextField.getText();
        String port = portTextField.getText();
        try {
            String url = hostname + ":" + port;
            manager = DBManager.createDBManager(url, user, password);
            InsubriaLoginController.setDbManager(manager);
            Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("insubria_login_pane.fxml"));
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root1);
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.setOnCloseRequest((WindowEvent event1) -> {
                Platform.exit();
                System.exit(0);
            });
            Stage oldStage = (Stage) confirmButton.getScene().getWindow();
            oldStage.close();

        } catch (SQLException e) {
            Notification.notify("Connection Notification", "Connessione non riuscita \nriprovare", true);
        }

    }
}
