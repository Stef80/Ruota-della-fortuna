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

    public PrimePane(Server server){
        this.server = server;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main_pane.fxml"));
        Scene scene = new Scene(root);
<<<<<<< HEAD
      //  scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Weel of Fortune");
=======
        primaryStage.setTitle("Wheel of Fortune");
>>>>>>> 2f3d956de427ec6cdfe7a11882507974e68e78a9
        primaryStage.setScene(scene);
        primaryStage.show();
    }

<<<<<<< HEAD
=======

>>>>>>> 2f3d956de427ec6cdfe7a11882507974e68e78a9
}
