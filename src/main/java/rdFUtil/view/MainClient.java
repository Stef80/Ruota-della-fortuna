package rdFUtil.view;

import javafx.application.Application;
import serverRdF.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient {

	public static void main(String[] args) throws Exception{
		Registry registryClient = LocateRegistry.getRegistry();
		Server server = (Server)registryClient.lookup("server") ;

		new Controller(server);
		new ForgottenPasswordPane(server);
		new GameView(server);
		new PrimePane(server);
		new RegistrationFormPanel(server);
		new TabPane(server);


		Application.launch(PrimePane.class,args);
	}


}
