package rdFUtil.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rdFUtil.ApplicationCloser;
import rdFUtil.Notification;
import rdFUtil.client.AdminChecker;
import rdFUtil.client.Client;
import serverRdF.Server;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Controller della finestra iniziale di PlayerRdF e AdminRdF. Inserendo l'hostname del server sara' possibile creare una connessione
 */
public class WelcomePane {
    @FXML
    private TextField hostnameTextField;
    @FXML
    private Button confirmButton;

    private Registry registry;
    private static Server server;
    private static Client client;

    /**
     * Questo metodo controlla l'hostname inserito e prova a stabilire una connessione. In caso di riuscita verra' caricata la schermata di login, in caso
     * contrario viene segnalato l'errore
     */
    public void startGameView() {
        String host = hostnameTextField.getText();
        try {
            registry = LocateRegistry.getRegistry(host, 1099);
            server = (Server) registry.lookup("SERVER");
            Parent root1 = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root1);
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            ApplicationCloser.setCloser(primaryStage);
            Stage oldStage = (Stage) confirmButton.getScene().getWindow();
            oldStage.close();
        } catch (RemoteException | NotBoundException e) {
            Notification.notify("Connection Notification", "Connessione non riuscita \nriprovare", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo utilizzato per passare le informazioni del client a {@link Controller}
     *
     * @param c il riferimento al controller {@link Controller}
     */
    public static void setController(Controller c) {
        c.setServer(server);
        c.setAdmin(AdminChecker.isIsAdmin());
    }
}
