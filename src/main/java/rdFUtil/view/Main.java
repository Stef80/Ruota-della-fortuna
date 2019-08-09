package rdFUtil.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/resources/sampleScene.css").toExternalForm());
        primaryStage.setTitle("Weel of Fortune");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
