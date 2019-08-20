package serverRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import rdFUtil.logging.CryptPassword;
import rdFUtil.logging.Login;
import rdFUtil.logging.User;
import serverRdF.matchRdF.RemoteMatch;
import serverRdF.registrationRdF.OTPHelper;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote {

   //ritorna true se la mail esiste già , false altrimenti.
    public boolean checkEMail(String email)throws RemoteException;

   //ritorna true se il nick esiste già , false altrimenti.
    public boolean checkNickName(String name)throws RemoteException;

    public OTPHelper signUp(User form, Client c) throws RemoteException;

    public void signIn(Login form, Client c) throws RemoteException;

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

    public void resetPassword(Client c, String mail) throws RemoteException;

    public boolean checkPassword(String password);
}
