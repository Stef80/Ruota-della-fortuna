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

    public int signIn(Login form, Client c, boolean admin) throws RemoteException; //TODO anche signIn dovrà avere il boolean e gestire in modo separato a seconda se il login avviene su PlayerRdF o adminRdF

    public ArrayList<MatchData> visualizeMatch(Client c) throws RemoteException;

    public RemoteMatch createMatch(Client c) throws RemoteException;

    public RemoteMatch joinMatch(Client c, String idMatch) throws RemoteException;

    public RemoteMatch observeMatch(Client c, String idMatch) throws RemoteException;

    public void addPhrases(File file, Client c) throws RemoteException;

    public boolean changeName(String name, Client c) throws RemoteException;

    public boolean changeSurname(String surname, Client c) throws RemoteException;

    public boolean changeNickname(String nickname, Client c) throws RemoteException;

    public boolean changePassword(String password, Client c) throws RemoteException;

    public void checkPlayer(Client c) throws RemoteException;

    public void checkPerPlayer(String nickname, Client c) throws RemoteException;

    /**
     * Ritorna la mossa che ha fatto ottenere il maggior numero di punti
     *
     * @return La mossa indicando il nickname del giocatore, la frase associata e la consonante chiamata
     * @throws RemoteException
     */
    public String bestMove() throws RemoteException;

    public void averageManches(Client c) throws RemoteException;

    public void resetPassword(Client c, String mail) throws RemoteException;
}
