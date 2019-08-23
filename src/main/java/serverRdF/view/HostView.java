package serverRdF.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostView {

	@FXML
	private Label hostnameLabel;

	public void takeAddress(){
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		hostnameLabel.setText(address.getHostName());
	}
}
