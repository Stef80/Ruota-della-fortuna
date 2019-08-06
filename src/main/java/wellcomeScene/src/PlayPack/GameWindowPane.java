package wellcomeScene.src.PlayPack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class GameWindowPane {
	@FXML
	private Button jollyButton;
	@FXML
	private Button vowelButton;
	@FXML
	private Button turnButton;
	@FXML
	private Button solutionButton;
	@FXML
	private Button exitButton;


	public void setAllDisable() {
		jollyButton.setDisable(true);
		vowelButton.setDisable(true);
		turnButton.setDisable(true);
		solutionButton.setDisable(true);
	}

	public void hideButton(){
      exitButton.setVisible(false);
	}
}