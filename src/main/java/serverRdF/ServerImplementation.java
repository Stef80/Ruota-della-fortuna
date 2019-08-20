package serverRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import rdFUtil.logging.Login;
import rdFUtil.logging.User;
import serverRdF.matchRdF.RemoteMatch;
import serverRdF.registrationRdF.OTPHelper;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImplementation extends UnicastRemoteObject implements Server {

    public ServerImplementation() throws RemoteException {
    }

    //TODO campi per i vari managers e implementazione metodi dell'interfaccia. estende UnicastRemoteObject e implementa Server


    /**
     * Questo metodo si occupa di gestire la notifica al client nel caso di errori con la connessione al server o al database.
     * <p>
     * Viene richiamato in caso di eccezioni come RemoteException o SQLException
     *
     * @param c riferimento al client
     */
    public static void serverError(Client c) {
        if (c == null) {
            System.out.println("Server error");
        } else {
            try {
                c.notifyServerError();
            } catch (RemoteException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public boolean checkEMail(String email) throws RemoteException {
        return false;
    }

    @Override
    public boolean checkNickname(String nickname) throws RemoteException {
        return false;
    }

    @Override
    public OTPHelper signUp(User form, Client c, boolean admin) throws RemoteException {
        return null;
    }

    @Override
    public boolean signIn(Login form, Client c, boolean admin) throws RemoteException {
        return false;
    }

    @Override
    public ArrayList<MatchData> visualizeMatch(Client c) throws RemoteException {
        return null;
    }

    @Override
    public RemoteMatch createMatch(Client c) throws RemoteException {
        return null;
    }

    @Override
    public RemoteMatch joinMatch(Client c, String idMatch) throws RemoteException {
        return null;
    }

    @Override
    public RemoteMatch observeMatch(Client c, String idMatch) throws RemoteException {
        return null;
    }

    @Override
    public void addPhrases(File file, Client c) throws RemoteException {
    }

    @Override
    public void changeName(String name, Client c) throws RemoteException {
    }

    @Override
    public void changeSurname(String surname, Client c) throws RemoteException {
    }

    @Override
    public void changeNickname(String nickname, Client c) throws RemoteException {
    }

    @Override
    public void changePassword(String password, Client c) throws RemoteException {
    }

    @Override
    public void checkPlayer(Client c) throws RemoteException {
    }

    @Override
    public void checkPerPlayer(String nickname, Client c) throws RemoteException {
    }

    @Override
    public void bestMove(Client c) throws RemoteException {
    }

    @Override
    public void averageManches(Client c) throws RemoteException {
    }

    @Override
    public void resetPassword(Client c) throws RemoteException {
    }
}
