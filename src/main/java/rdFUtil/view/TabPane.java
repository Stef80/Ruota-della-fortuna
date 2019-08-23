package rdFUtil.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.Match;
import serverRdF.matchRdF.RemoteMatch;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TabPane implements Initializable {

    @FXML
    private ListView<MatchData> gameList;

    private ObservableList<MatchData> gameObservableList = FXCollections.observableArrayList();
    private Client client;
    private Server server;
    private RemoteMatch match;


    public TabPane(){}

    public TabPane(Server server, Client client) {
        this.server = server;
        this.client = client;
    }

    public void addMatch(ActionEvent actionEvent) throws RemoteException, NotBoundException {
    	match = server.createMatch(client);

        FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("game_player_pane.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GamePlayerController game = loader.getController();
        game.createTableOfPhrase();
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
		ArrayList<MatchData> list = null;
		try {
			list = server.visualizeMatch(client);
			gameList.setItems(gameObservableList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (MatchData matchData: list){
			gameList.setCellFactory(e -> new GameView(server, client, matchData));
		}



//        gameList.setCellFactory(new Callback<ListView<Game>, ListCell<Game>>() {
//            @Override
//            public ListCell<Game> call(ListView<Game> listGame) {
//                return new GameView;
//            }
//        });
    }

}



