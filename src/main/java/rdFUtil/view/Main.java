package rdFUtil.view;

import javafx.application.Application;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;
import serverRdF.Server;
import serverRdF.ServerImplementation;
import serverRdF.matchRdF.Match;
import serverRdF.matchRdF.RemoteMatch;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) throws Exception{
		Client client = new ClientImplementation() ;


		/*Registry registryClient = LocateRegistry.getRegistry();
		Server server = (Server)registryClient.lookup("server") ;*/
		//prove di avvio
		Server server = new ServerImplementation();
        RemoteMatch match = new Match("id", LocalDateTime.now());
        //prove di avvio
		new Controller(server, client);
		new ForgottenPasswordPane(server , client);
		new GameView(server,client,match);
		new PrimePane(server);
		new RegistrationFormPanel(server, client);
		new TabPane(server,client);


		Application.launch(PrimePane.class,args);
	}


}
