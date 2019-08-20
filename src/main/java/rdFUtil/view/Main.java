package rdFUtil.view;

import javafx.application.Application;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;
import serverRdF.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) throws Exception {
        Client client = new ClientImplementation();

        Registry registryClient = LocateRegistry.getRegistry();
        Server server = (Server) registryClient.lookup("server");

        new Controller(server, client);
        new ForgottenPasswordPane(server, client);
        new GameView(server);
        new PrimePane(server);
        new RegistrationFormPanel(server, client);
        new TabPane(server);


        Application.launch(PrimePane.class, args);
    }
}
