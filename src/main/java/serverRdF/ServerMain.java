package serverRdF;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static final int PORT = 1099;

    public static void main(String[] args) {
        /*
        TODO
        aprire la schermata per inserire host-utente-password del DB
        provare ad accedere al db
        creare il DBManager e vedere se si può effettivamente creare provando la connessione
        if(dbManager.getAllAdmin().size() == 0)
            aprire la schermata di registrazione per admin
        aprire schermata di login per admin e aspettare l'accesso
        ottenere le credenziali per account insubria (visto che salvarle nel database poi crea il problema nel caso si cambi la password dell'account,
        probabilmente è meglio togliere quella tabella dal database e limitarsi a effettuare l'accesso tramite l'invio di una mail all'account a cui si prova ad accedere.
        Se da eccezione significa che l'autenticazione non è riuscita e bisogna riprovare)
        creare il EmailManager con le credenziali ottenute
        creare l'oggetto ServerImpl(dbManager) che creerà tutti i singleton
        InetAddress address = InetAddress.getLocalHost();
        String hostName = address.getHostName();
        Registry r;
        if((r = LocateRegistry.getRegistry(PORT)) == null){
            r = LocateRegistry.createRegistry(PORT);
        }
        r.rebind("SERVER", serverImpl);
        stampare sulla schermata del server l'hostname' in modo che i client lo possano usare per ricercare il server
         */
    }
}
