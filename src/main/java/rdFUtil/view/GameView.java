package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.Match;
import serverRdF.matchRdF.RemoteMatch;

import java.io.IOException;
import java.rmi.RemoteException;

public class GameView extends ListCell<MatchData> {
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label availableLabel;
    @FXML
    private Button observeButton;
    @FXML
    private Button joinButton;
    private FXMLLoader loader;
    private Parent pane;
    private Server server;
    private Client client;
    private MatchData matchData;
    private RemoteMatch match;

    public GameView(){}

    public GameView(Server server, Client client,MatchData matchData) {
        this.server = server;
        this.client = client;
        this.matchData = matchData;
    }

    @Override
    protected void updateItem(MatchData item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("game_list_pane.fxml"));
                loader.setController(this);
                try {
                    pane = loader.load();
                    Scene scene = new Scene(pane);
                    //	scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            label1.setText(item.getPlayer1());
			label2.setText(item.getPlayer2());
			label3.setText(item.getPlayer3());
            setAviableLabel(!item.isOnGoing());

            joinButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        match = server.joinMatch(client, item.getIdMatch());
                        GamePlayerController.setMatch(match);
                        GamePlayerController.setObserver(false);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (match == null) {
                        Notifications notification = Notifications.create()
                                .title("")
                                .text("")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.CENTER);
                        notification.showError();
                    }
<<<<<<< HEAD
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("game_player_Pane.fxml"));
=======
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("game_player_pane.fxml"));
>>>>>>> 9e92c04d8da6be9ae450345842a7d2512bcff307
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GamePlayerController game = loader.getController();
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }
            });
            observeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    try {
                        match.addObserver(client);
                        GamePlayerController.setMatch(match);
                        GamePlayerController.setObserver(true);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("game_player_pane.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GamePlayerController game = loader.getController();
                    game.hideAll();
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }
            });
        }
        setGraphic(pane);


    }

    private void setAviableLabel(boolean aviable) {
        if (aviable) {
            availableLabel.setText("available");
        } else {
            availableLabel.setText(null);
            joinButton.setDisable(true);
        }
    }

}
