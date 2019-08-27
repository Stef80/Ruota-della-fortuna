package serverRdF.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import serverRdF.Server;
import serverRdF.ServerImplementation;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

/**
 * Controlla la schermata principale del server, la quale visualizza l'hostname necessario ai client per accedere alla piattaforma
 */
public class HostViewController implements Initializable {

	@FXML
	private Label hostnameLabel;
	private Registry r;
	private Server server;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		InsubriaLoginController.setHost(this);
	}

	/**
	 * Metodo che si occupa di stampare l'hostname della macchina su cui gira il server
	 *
	 * @throws Exception
	 */
	public void takeAddress() throws Exception{
		r.rebind("SERVER",server);
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
			hostnameLabel.setText("Hostname: " + address.getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public Registry getR() {
		return r;
	}

	public Server getServer() {
		return server;
	}

	public void setR(Registry r) {
		this.r = r;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
