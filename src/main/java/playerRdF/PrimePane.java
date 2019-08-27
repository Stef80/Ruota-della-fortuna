package playerRdF;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rdFUtil.client.AdminChecker;
import rdFUtil.view.FrameTitle;

/**
 * La classe che avvia il modulo PlayerRdF
 */
public class PrimePane extends Application {
    public PrimePane(){}

    public static void main(String[] args) throws Exception {
        AdminChecker.setIsAdmin(false);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AdminChecker.setIsAdmin(false);
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("welcome_pane.fxml"));
        //Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
