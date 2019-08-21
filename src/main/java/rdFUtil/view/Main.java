package rdFUtil.view;

import javafx.application.Application;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;
import serverRdF.Server;
import serverRdF.ServerImplementation;
import serverRdF.matchRdF.Match;
import serverRdF.matchRdF.RemoteMatch;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws Exception {
        Client client = new ClientImplementation();

//        Registry registryClient = LocateRegistry.getRegistry();
//        Server server = (Server) registryClient.lookup("server");
        Server server = new ServerImplementation();
//        RemoteMatch match = new Match("id", LocalDateTime.now());//TODO da aggiungere DBManager e EmailManager
        //il terzo argomento dipende da dove viene avviato (se AdminRdF o PlayerRdF)
        //TODO new Controller(server, client);
        new ForgottenPasswordPane(server, client);
//        new GameView(server, client, match);//TODO da decommentare quano risolto il costruttore di RemoteMatch
        new PrimePane(server);
        new RegistrationFormPanel(server, client);
//        new TabPane(server, client);//todo da recuperare modifiche di TabPane


        Application.launch(PrimePane.class, args);
    }
}
