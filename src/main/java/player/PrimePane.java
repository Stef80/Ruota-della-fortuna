package player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.client.AdminChecker;
import util.view.FrameTitle;

/**
 * La classe che avvia il modulo PlayerRdF
 */
public class PrimePane extends Application {

    public PrimePane() {
    }

    public static void main(String[] args) throws Exception {
        AdminChecker.setIsAdmin(false);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AdminChecker.setIsAdmin(false);
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("welcome_pane.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
