package wellcomeScene.src.PlayPack;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
	private Label validateLabel;
	@FXML
	private Button observeButton;
	@FXML
	private Button joinButton;
	@FXML
	private ImageView weel;
	@FXML
	private Button jollyButton;

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
				validateLabel.setText("aviable");
			}else{
				validateLabel.setText(null);
			}
			joinButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent event) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindowPane.fxml"));
					Parent root  = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					GameWindowPane gameWindowPane = loader.getController();
					gameWindowPane.hideButton();
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});
			observeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent event) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindowPane.fxml"));
					Parent root  = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    GameWindowPane gameWindowPane = loader.getController();
					gameWindowPane.setAllDisable();
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});

			setGraphic(pane);
		}

	}

}
