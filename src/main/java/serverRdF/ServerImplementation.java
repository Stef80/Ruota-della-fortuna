package serverRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import rdFUtil.logging.Login;
import rdFUtil.logging.User;
import serverRdF.dbComm.DBManager;
import serverRdF.emailRdF.EmailManager;
import serverRdF.matchRdF.MatchManager;
import serverRdF.matchRdF.RemoteMatch;
import serverRdF.registrationRdF.OTPHelper;
import serverRdF.registrationRdF.RegistrationManager;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImplementation extends UnicastRemoteObject implements Server {
    private DBManager dbManager;
    private EmailManager emailManager;
    private ProfileManager profileManager;
    private PhraseManager phraseManager;
    private MonitoringManager monitoringManager;
    private MatchVisualizer matchVisualizer;
    private MatchManager matchManager;
    private AutenticationManager autenticationManager;
    private RegistrationManager registrationManager;

    public ServerImplementation(DBManager dbmng, EmailManager emailmang) throws RemoteException {
        dbManager = dbmng;
        emailManager = emailmang;
        profileManager = ProfileManager.createProfileManager(dbManager, emailManager);
        phraseManager = PhraseManager.createPhraseManager(dbManager);
        monitoringManager = MonitoringManager.createMonitoringManager(dbManager);
        matchManager = MatchManager.createMatchManager(dbManager,emailManager);
        matchVisualizer = MatchVisualizer.createMatchVisualizer(matchManager);
        autenticationManager = AutenticationManager.createAutenticationManager(dbManager);
        registrationManager = RegistrationManager.createRegistrationManager(dbManager, emailManager);
    }

    //TODO implementazione metodi dell'interfaccia.


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
        return registrationManager.checkEmail(email);
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
    public int signIn(Login form, Client c, boolean admin) throws RemoteException {
        return 0;
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
    public boolean changeName(String name, Client c) throws RemoteException {
        String idUser = c.getId();
        return profileManager.changeName(name, idUser);
    }

    @Override
    public boolean changeSurname(String surname, Client c) throws RemoteException {
        String idUser = c.getId();
        return profileManager.changeSurname(surname, idUser);
    }

    @Override
    public boolean changeNickname(String nickname, Client c) throws RemoteException {
        String idUser = c.getId();
        return profileManager.changeNickname(nickname, idUser);
    }

    @Override
    public boolean changePassword(String password, Client c) throws RemoteException {
        String idUser = c.getId();
        return profileManager.changePassword(password, idUser);
    }

    @Override
    public String checkPlayer() throws RemoteException {
        return null;
    }

    @Override
    public String checkPerPlayer(String nickname) throws RemoteException {
        return null;
    }

    @Override
    public String bestMove() throws RemoteException {
        return null;
    }

    @Override
    public int averageManches() throws RemoteException {
        return 0;
    }

    @Override
    public boolean resetPassword(String mail) throws RemoteException {
        return profileManager.resetPassword(mail);
    }
}
