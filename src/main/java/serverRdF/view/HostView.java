package serverRdF.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import serverRdF.ServerImplementation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;

public class HostView {

	@FXML
	private Label hostnameLabel;

	public void takeAddress() throws Exception{
		Registry r = InsubriaLoginPane.getRegistry();
		ServerImplementation server = InsubriaLoginPane.getServer();
		r.rebind("SERVER",server);
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		hostnameLabel.setText(address.getHostName());
	}
}
