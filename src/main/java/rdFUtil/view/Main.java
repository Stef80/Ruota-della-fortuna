package rdFUtil.view;

import javafx.application.Application;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;
import serverRdF.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

	public static void main(String[] args) throws Exception{
		Client client = new ClientImplementation() ;

		Registry registryClient = LocateRegistry.getRegistry();
		Server server = (Server)registryClient.lookup("server") ;

		new Controller(server, client);
		new ForgottenPasswordPane(server , client);
<<<<<<< HEAD
		//new GameView(server,client,match);
=======
		new GameView(server);
>>>>>>> 2f3d956de427ec6cdfe7a11882507974e68e78a9
		new PrimePane(server);
		new RegistrationFormPanel(server, client);
		new TabPane(server);


		Application.launch(PrimePane.class,args);
	}


}
