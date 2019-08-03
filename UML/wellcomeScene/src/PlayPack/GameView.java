package PlayPack;

import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class GameView extends ListCell<Game> {
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Button observeButton;
	@FXML
	private Button joinButton;
	@FXML
	private ImageView weel;

	private FXMLLoader loader;
	private AnchorPane pane;

	@Override
	protected void updateItem(Game item, boolean empty) {
		super.updateItem(item, empty);

		if(empty || item == null) {
			setText(null);
			setGraphic(null);
		}else{
			if(loader == null){
				loader = new FXMLLoader(getClass().getResource("/PlayPack/gameView.fxml"));

				loader.setController(this);
				try {
					  pane = loader.load();
					  Scene scene = new Scene(pane);
					scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			label1.setText(item.getNamePlayier());
			label2.setText(item.getId());
			if(item.isAviable()){
				label3.setText("aviable");
			}else{
				label3.setText(null);
			}

			setGraphic(pane);
		}




	}
}
