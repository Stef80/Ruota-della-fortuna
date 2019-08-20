package rdFUtil.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverRdF.Server;

public class PrimePane extends Application {
    @FXML
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private Server server;

    public PrimePane(Server server) {
        this.server = server;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Wheel of Fortune");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
