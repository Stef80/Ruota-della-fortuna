package rdFUtil.view;

import javafx.application.Application;
import rdFUtil.client.Client;
import rdFUtil.client.ClientImplementation;

public class Main {

    public static void main(String[] args) throws Exception {

          Client client = new ClientImplementation();
//        Registry registryClient = LocateRegistry.getRegistry();
//        Server server = (Server) registryClient.lookup("server");
//        Server server = new ServerImplementation();//TODO da aggiungere DBManager e EmailManager
//        RemoteMatch match = new Match("id", LocalDateTime.now());//TODO da aggiungere DBManager e EmailManager
        //il terzo argomento dipende da dove viene avviato (se AdminRdF o PlayerRdF)
        //TODO new Controller(server, client);
//        new ForgottenPasswordPane(server, client);//TODO da decommentare quano risolto il costruttore di ServerImplementation
//
//        new PrimePane(server);//TODO da decommentare quano risolto il costruttore di ServerImplementation
//        new RegistrationFormPanel(server, client);//TODO da decommentare quano risolto il costruttore di ServerImplementation
//        new TabPane(server, client);//todo da recuperare modifiche di TabPane


        Application.launch(PrimePane.class, args);
    }
}
