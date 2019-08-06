package firstPack;

import PlayPack.Game;
import PlayPack.GameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TabPane implements Initializable {

	@FXML
	private ListView<Game> gameList;

	private ObservableList<Game> gameObservableList = FXCollections.observableArrayList();

	public void addMatch(ActionEvent actionEvent){

		Game game = new Game("stafano", "122");
		gameObservableList.add(game);
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



