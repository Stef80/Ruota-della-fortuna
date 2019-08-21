package rdFUtil.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class TabPane implements Initializable {

    @FXML
    private ListView<MatchData> gameList;

    private ObservableList<MatchData> gameObservableList = FXCollections.observableArrayList();
    private Client client;
    private Server server;
    private RemoteMatch match;

    public TabPane(Server server, Client client) {
        this.server = server;
        this.client = client;
    }

    public void addMatch(ActionEvent actionEvent) throws RemoteException, NotBoundException {
        server.createMatch(client);
//		match = new Match("id",LocalDateTime.now());//todo da aggiungere DBManager e EmailManager

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameList.setItems(gameObservableList);
        gameList.setCellFactory(e -> new GameView(server, client, match));

//        gameList.setCellFactory(new Callback<ListView<Game>, ListCell<Game>>() {
//            @Override
//            public ListCell<Game> call(ListView<Game> listGame) {
//                return new GameView;
//            }
//        });
    }
}



