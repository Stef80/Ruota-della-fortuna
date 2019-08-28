package dbuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rdFUtil.view.FrameTitle;

import java.io.IOException;

public class DatabaseBuilder extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("database_builder.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle(FrameTitle.main);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
