package serverRdF.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serverRdF.dbComm.DBManager;

public class PrimePaneServer extends Application {



	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("server_main_pane.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Wheel of Fortune");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
