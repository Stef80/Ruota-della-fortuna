package rdFUtil.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import serverRdF.matchRdF.RemoteMatch;

import java.net.URL;
import java.util.ResourceBundle;

public class TabPane implements Initializable {

	@FXML
	private ListView<RemoteMatch> gameList;

	private ObservableList<RemoteMatch> gameObservableList = FXCollections.observableArrayList();

	public void addMatch(ActionEvent actionEvent){

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameList.setItems(gameObservableList);
		gameList.setCellFactory(e -> new GameView());
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



