package serverRdF;

import rdFUtil.client.Client;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class ServerImplementation {



    //TODO campi per i vari managers e implementazione metodi dell'interfaccia. estende UnicastRemoteObject e implementa Server


    /**
     *
     * @param c riferimento al client
     *
     * Questo metodo si occupa di gestire la notifica al client nel caso di errori con la connessione al server o al database.
     * Viene richiamato in caso di eccezioni come RemoteException o SQLException
     */
    public static void serverError(Client c){
        try {
            c.notifyServerError();
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
