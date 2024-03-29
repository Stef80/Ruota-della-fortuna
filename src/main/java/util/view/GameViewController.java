package util.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.ApplicationCloser;
import util.MatchData;
import util.Notification;
import util.client.AdminChecker;
import util.client.Client;
import server.Server;
import server.match.RemoteMatch;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Controller degli elementi della lista dei match in corso e in attesa di giocatori. Contiene informazioni utili come i giocatori che gia' partecipano
 * alla partita e se la partita e' gia' in corso. Ogni elemento GameView ha i propri bottoni per partecipare come osservatore o giocatore
 */
public class GameViewController extends ListCell<MatchData> {
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label aviableLabel;
    @FXML
    private Button observeButton;
    @FXML
    private Button joinButton;
    private FXMLLoader loader;
    private Parent pane;
    private Server server;
    private static Client client;
    private MatchData matchData;
    private static RemoteMatch match;
    public static boolean player;

    public GameViewController() {
    }

    public GameViewController(Server server, Client client, MatchData matchData) {
        this.server = server;
        this.client = client;
        this.matchData = matchData;
    }

    /**
     * Aggiorna i gli elementi con i dati del match. Il tasto per partecipare alla partita sara' disponibiile sono se il match
     * e' disponibile e l'utente connesso non e' un admin.
     * Il tasto di join permette di partecipare alla partita, il tasto observe di parteciparvi da osservatore.In entrambi i casi verra'
     * aperta la finestra di gioco.
     * //TODO scrittura parametri
     *
     * @param item
     * @param empty
     */

    @Override
    protected void updateItem(MatchData item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("game_list_pane.fxml"));
                loader.setController(this);
                try {
                    pane = loader.load();
                    Scene scene = new Scene(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (AdminChecker.isIsAdmin())
                joinButton.setVisible(false);

            label1.setText(item.getPlayer1());
            label2.setText(item.getPlayer2());
            label3.setText(item.getPlayer3());
            setAviableLabel(!item.isOnGoing());

            joinButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        player = true;
                        match = server.joinMatch(client, item.getIdMatch());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (match == null) {
                        Notification.notify("Notifica Partita", "Partita inesistente", true);
                    } else {
                        TabPaneController.creator = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game_player_pane.fxml"));
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
                        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
                            try {
                                match.leaveMatchAsPlayer(client);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        });

                        Stage oldStage = (Stage) joinButton.getScene().getWindow();
                        oldStage.close();
                    }
                }
            });
            observeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    player = false;
                    try {
                        match = server.observeMatch(client, item.getIdMatch());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (match == null) {
                        Notification.notify("Notifica Partita", "Partita inesistente", true);
                    } else {
                        TabPaneController.creator = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game_player_pane.fxml"));
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
                        ApplicationCloser.setCloser(primaryStage);
                        Stage oldStage = (Stage) observeButton.getScene().getWindow();
                        oldStage.close();
                    }
                }
            });
        }
        setGraphic(pane);


    }

    private void setAviableLabel(boolean aviable) {
        if (aviable) {
            aviableLabel.setText("available");
        } else {
            aviableLabel.setText(null);
            joinButton.setDisable(true);
        }
    }

    /**
     * Metodo utilizzato per passare le informazioni del client a {@link GamePlayerController}
     *
     * @param gpc il riferimento al controller {@link GamePlayerController}
     */
    public static void setGameControllerObserver(GamePlayerController gpc) {
        gpc.setClient(client);
        gpc.setMatch(match);
        gpc.setObserver(!GameViewController.player);
    }
}
