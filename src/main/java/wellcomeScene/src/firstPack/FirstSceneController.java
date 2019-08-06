package wellcomeScene.src.firstPack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstSceneController {
	@FXML
	private Button gamesButton;
	@FXML
	private Button statisticButton;
	@FXML
	private Button profileButton;

	public void atGames(ActionEvent event) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("../PlayPack/playierList.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root1);
		scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
		primaryStage.setTitle("Weel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
