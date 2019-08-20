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

    public boolean signIn(Login form, Client c) throws RemoteException; //TODO anche signIn dovrà avere il boolean e gestire in modo separato a seconda se il login avviene su PlayerRdF o adminRdF

    public ArrayList<MatchData> visualizeMatch(Client c) throws RemoteException;

    public RemoteMatch createMatch(Client c) throws RemoteException;

    public RemoteMatch joinMatch(Client c, String idMatch) throws RemoteException;

    public RemoteMatch observeMatch(Client c, String idMatch) throws RemoteException;

    public void addPhrases(File file, Client c) throws RemoteException;

    public void changeName(String name, Client c) throws RemoteException;

    public void changeSurname(String surname, Client c) throws RemoteException;

    public void changeNickname(String nickname, Client c) throws RemoteException;

    public void changePassword(String password, Client c) throws RemoteException;

    public void checkPlayer(Client c) throws RemoteException;

    public void checkPerPlayer(String nickname, Client c) throws RemoteException;

    public void bestMove(Client c) throws RemoteException;

    public void averageManches(Client c) throws RemoteException;

    public void resetPassword(Client c) throws RemoteException;
}
