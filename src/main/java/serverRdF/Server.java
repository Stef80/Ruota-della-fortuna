package serverRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.CryptedPassword;
import rdFUtil.logging.Login;
import rdFUtil.logging.User;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    public void signUp(User form, Client c) throws RemoteException;

    public void signIn(Login form, Client c) throws RemoteException;

    public void visualizeMatch(Client c) throws RemoteException;

    public void createMatch(Client c) throws RemoteException;

    public void joinMatch(Client c, String idMatch) throws RemoteException;

    public void observeMatch(Client c, String idMatch) throws RemoteException;

    public void addPhrases(File file, Client c) throws RemoteException;

    public void changeName(String name, Client c) throws RemoteException;

    public void changeSurname(String surname, Client c) throws RemoteException;

    public void changeNickname(String nickname, Client c) throws RemoteException;

    public void changePassword(CryptedPassword password, Client c) throws RemoteException;

    public void checkPlayer(Client c) throws RemoteException;

    public void checkPerPlayer(String nickname, Client c) throws RemoteException;

    public void bestMove(Client c) throws RemoteException;

    public void averageManches(Client c) throws RemoteException;

    public void resetPassword(Client c) throws RemoteException;
}