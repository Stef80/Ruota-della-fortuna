package serverRdF.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import serverRdF.dbComm.DBManager;


public class ServerMainPane {
    @FXML
	private TextField userTextField;
    @FXML
	private PasswordField passwordTextField;
    @FXML
	private TextField hostnameTextField;
    @FXML
	private Button confirmButton;
     private DBManager manager;


    public void login(){
    	String user = userTextField.getText();
    	String password = passwordTextField.getText();
    	String hostname = hostnameTextField.getText();
    	manager = DBManager.createDBManager();
	}
}
