package serverRdF.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rdFUtil.view.FrameTitle;

/**
 * La classe che avvia il modulo ServerRdF
 */
public class PrimePaneServer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("server_main_pane.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
            System.exit(0);
        });
    }
}
