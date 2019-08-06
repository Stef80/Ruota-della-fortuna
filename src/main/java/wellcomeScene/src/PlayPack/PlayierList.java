package wellcomeScene.src.PlayPack;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayierList implements Initializable {

	@FXML
	private ListView<Game> gameList;

	private ObservableList<Game> gameObservableList = FXCollections.observableArrayList(
			new Game("STefano", "1"),
			new Game("Giacomo", "2"),
			new Game("Paolo","3"));



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
