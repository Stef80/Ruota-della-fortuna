package rdFUtil.client;


import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Interfaccia dell'oggetto remoto utilizzato da PlayerRdF e AdminRdF per ricevere notifiche dal server
 */
public interface Client extends Remote {
    /**
     * Questo metodo permette di ottenere il nickname dell'utente loggato nel client
     *
     * @return il nickname
     * @throws RemoteException
     */
    public String getNickname() throws RemoteException;

    /**
     * Questo metodo permette di ottenere l'id dell'utente loggato nel client
     *
     * @return l'id utente
     * @throws RemoteException
     */
    public String getId() throws RemoteException;

    /**
     * Permette di settare il nickname dell'utente loggato nel client
     *
     * @throws RemoteException
     */
    public void setNickname() throws RemoteException;

    /**
     * Permette di settare il nickname dell'utente loggato nel client
     * @throws RemoteException
     */
    public void setId() throws RemoteException;

    /**
     * Notifica al client che c'e' stato un problema di connessione al server da parte del client o al database da parte del server
     *
     * @throws RemoteException
     */
    public void notifyServerError() throws RemoteException;

    /**
     * Notifica se e' stato possibile concludere la registrazione
     *
     * @param success <code>true</code> se la registrazione e' avvenuta con successo, <code>false</code> altrimenti
     * @throws RemoteException
     */
    public void notifyRegistrationResult(boolean success) throws RemoteException;


    /**
     * Notifica che il codice inserito per ultimare la registrazione e' errato
     *
     * @throws RemoteException
     */
    public void notifyWrongOTP() throws RemoteException;

    /**
     * Notifica che non è stato possibile accedere alla partita poiche' e' piena
     *
     * @throws RemoteException
     */
    public void notifyTooManyPlayers() throws RemoteException;

    /**
     * Notifica che uno degli altri giocatori ha abbandonato la partita
     *
     * @param nickname il nickname di chi ha abbandonato
     * @throws RemoteException
     */
    public void notifyLeaver(String nickname) throws RemoteException;

    /**
     * Notifica che la partita e' stata annullata
     *
     * @param reason il motivo per cui la partita e' stata annullata
     * @throws RemoteException
     */
    public void notifyMatchAbort(String reason) throws RemoteException;

    /**
     * Notifica l'inizio della partita
     *
     * @throws RemoteException
     */
    public void notifyMatchStart() throws RemoteException;

    /**
     * Notifica che il client è colui che ha vinto la manche
     *
     * @throws RemoteException
     */
    public void notifyMancheVictory() throws RemoteException;

    /**
     * Notifica il client che <code>winner</code> ha vinto la manche
     *
     * @param winner il nickname del vincitore della manche
     * @throws RemoteException
     */
    public void notifyMancheResult(String winner) throws RemoteException;

    /**
     * Notifica al client l'inizio di una nuova manche
     *
     * @param numManche il numero dell manche appena iniziata
     * @throws RemoteException
     */
    public void notifyNewManche(int numManche) throws RemoteException;
}
