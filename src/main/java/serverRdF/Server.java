package serverRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import rdFUtil.logging.Login;
import rdFUtil.logging.User;
import serverRdF.matchRdF.RemoteMatch;
import serverRdF.registrationRdF.OTPHelper;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote {
    /**
     * Questo metodo controlla se c'e' gia' un utente registrato con la mail fornita
     *
     * @param email la mail dell'utente
     * @return <code>false</code> se la mail esiste gia', <code>true</code> altrimenti
     * @throws RemoteException
     */
    public boolean checkEMail(String email) throws RemoteException;

    /**
     * Questo metodo controlla se c'e' gia' un utente registrato con il nickname fornito
     *
     * @param nickname la mail dell'utente
     * @return <code>false</code> se il nickname esiste gia', <code>true</code> altrimenti
     * @throws RemoteException
     */
    public boolean checkNickname(String nickname) throws RemoteException;

    public OTPHelper signUp(User form, Client c, boolean admin) throws RemoteException;

    public int signIn(Login form, Client c, boolean admin) throws RemoteException;

    public ArrayList<MatchData> visualizeMatch(Client c) throws RemoteException;

    public RemoteMatch createMatch(Client c) throws RemoteException;

    public RemoteMatch joinMatch(Client c, String idMatch) throws RemoteException;

    public RemoteMatch observeMatch(Client c, String idMatch) throws RemoteException;

    public void addPhrases(File file) throws RemoteException;

    public boolean changeName(String name, Client c) throws RemoteException;

    public boolean changeSurname(String surname, Client c) throws RemoteException;

    public boolean changeNickname(String nickname, Client c) throws RemoteException;

    public boolean changePassword(String password, Client c) throws RemoteException;

    /**
     * Permette di ottenere i nomi dei concorrenti che possiedono dei record della piattaforma
     *
     * @return una stringa contenente, divisi da spazi, i nickname dei giocatori che: detengono il punteggio piu' alto per manche, detengono il punteggio piu' alto per partita,
     * che ha giocato piu' manche in assoluto, con la media di punti acquisiti per manche piu' alta, che ha ceduto il turno piu' volte a causa di errori, che ha perso tutto il maggior numero di volte
     * @throws RemoteException
     */
    public String checkRecordPlayer() throws RemoteException;

    /**
     * Metodo utilizzato per ottenere le statistiche riguardanti il singolo utente
     *
     * @param nickname il nickname dell'utente
     * @return una stringa contenente, divisi da spazi: numero manche giocate, numero partite giocate, numero manche osservate, numero match osservati,
     * numero manche vinte, numero match vinti, punteggio medio vinto per partita, numero medio di volte che ha dovuto cedere il turno di gioco per manche, numero medio di volte che ha dovuto cedere il turno di gioco per match,
     * numero medio di volte che ha perso tutto per manche, numero medio di volte che ha perso tutto per match
     * @throws RemoteException
     */
    public String checkPerPlayer(String nickname) throws RemoteException;

    /**
     * Ritorna la mossa che ha fatto ottenere il maggior numero di punti
     *
     * @return Una stringa contenente, divisi da spazi: il nickname del giocatore, la consonante chiamata e la frase associata
     * @throws RemoteException
     */
    public String bestMove() throws RemoteException;

    /**
     * Metodo che permette di ottenere il numero medio di mosse necessarie a indovinare una frase misteriosa
     *
     * @return il numero medio di mosse
     * @throws RemoteException
     */
    public int averageManches() throws RemoteException;

    /**
     * Permette il reset della password. Viene inviata una mail all'inidirizzo indicato con la nuova password
     *
     * @param mail l'indirizzo email dell'account da resettare
     * @return <code>true</code> se il reset avviene con successo, <code>false</code> se l'indirizzo email non esiste nel database
     * @throws RemoteException
     */
    public boolean resetPassword(Client c,String mail) throws RemoteException;
}
