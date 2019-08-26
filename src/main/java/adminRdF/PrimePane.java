package adminRdF;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rdFUtil.client.AdminChecker;

/**
 * La classe che avvia il modulo AdminRdF
 */
public class PrimePane extends Application {
    public PrimePane(){}

    public static void main(String[] args) throws Exception {
        AdminChecker.setIsAdmin(true);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AdminChecker.setIsAdmin(true);
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("wellcome_pane.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Wheel of Fortune");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
