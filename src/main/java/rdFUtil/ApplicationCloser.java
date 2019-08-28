package rdFUtil;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ApplicationCloser {
    public static void setCloser(Stage stage){
        stage.setOnCloseRequest((WindowEvent event1) -> {
            System.exit(0);
        });
    }
}
