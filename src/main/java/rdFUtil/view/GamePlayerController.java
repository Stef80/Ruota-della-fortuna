package rdFUtil.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GamePlayerController {
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
    @FXML
    private TextField solutionTextField;
    @FXML
    private TextField letterTextField;

    public void hideAll() {
        jollyButton.setVisible(false);
        vowelButton.setVisible(false);
        turnButton.setVisible(false);
        solutionButton.setVisible(false);
        solutionTextField.setDisable(true);
        letterTextField.setDisable(true);
    }

    public void hideButton() {
        exitButton.setVisible(false);
    }
}
