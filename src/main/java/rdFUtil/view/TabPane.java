package rdFUtil.view;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.Match;
import serverRdF.matchRdF.RemoteMatch;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class TabPane implements Initializable {

	@FXML
	private ListView<RemoteMatch> gameList;

	private ObservableList<RemoteMatch> gameObservableList = FXCollections.observableArrayList();

	private Client client;
	private Server server;
<<<<<<< HEAD

	public TabPane(Server server){
=======
	private RemoteMatch match;

	public TabPane(Server server, Client client){
>>>>>>> parent of d8b696f... aggiunta metodi senza argomenti
		this.server = server;
		this.client = client;
	}



	public void addMatch(ActionEvent actionEvent) throws RemoteException, NotBoundException {
        match = new Match("id",LocalDateTime.now());
        gameObservableList.add(match);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameList.setItems(gameObservableList);
		gameList.setCellFactory(e -> new GameView(server,client,match));
		/*
		 * gameList.setCellFactory(new Callback<ListView<Game>, ListCell<Game>>(){
		 *
		 * @override
		 * public ListCell<Game> call(ListView<Game> listGame){
		 * return new GameView;
		 * }
		 * });
		 * */
	}
}



