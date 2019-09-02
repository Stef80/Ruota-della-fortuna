package admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.client.AdminChecker;
import util.view.FrameTitle;

/**
 * La classe che avvia il modulo AdminRdF
 */
public class PrimePane extends Application {

    public PrimePane() {
    }

    public static void main(String[] args) throws Exception {
        AdminChecker.setIsAdmin(true);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AdminChecker.setIsAdmin(true);
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("welcome_pane.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
