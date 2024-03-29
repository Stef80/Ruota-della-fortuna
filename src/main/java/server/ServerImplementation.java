package server;

import util.MatchData;
import util.client.Client;
import util.logging.Login;
import util.logging.User;
import server.dbComm.DBManager;
import server.email.EmailManager;
import server.match.MatchManager;
import server.match.RemoteMatch;
import server.registration.OTPHelper;
import server.registration.RegistrationManager;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Implementazione dell'interfaccia remota Server.
 *
 */
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

    /**
     * Questo metodo si occupa di gestire la notifica al client nel caso di errori con la connessione al server o al database.
     * <p>
     * Viene richiamato in caso di eccezioni come RemoteException o SQLException
     *
     * @param c riferimento al client
     */
    public static void serverError(Client c) {
        if (c == null) {
            System.err.println("Server error");
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
        return registrationManager.checkNickname(nickname);
    }

    @Override
    public OTPHelper signUp(User form, Client c, boolean admin) throws RemoteException {
        return registrationManager.signUp(form,c,admin);
    }

    @Override
    public int signIn(Login form, Client c, boolean admin) throws RemoteException {
        return autenticationManager.signIn(form,c,admin);
    }

    @Override
    public ArrayList<MatchData> visualizeMatch(Client c) throws RemoteException {
        return matchVisualizer.visualizeMatch();
    }

    @Override
    public RemoteMatch createMatch(Client c) throws RemoteException {
        return matchManager.createMatch(c);
    }

    @Override
    public RemoteMatch joinMatch(Client c, String idMatch) throws RemoteException {
            return matchManager.joinMatch(c,idMatch);
    }

    @Override
    public RemoteMatch observeMatch(Client c, String idMatch) throws RemoteException {
        return matchManager.observeMatch(c,idMatch);
    }

    @Override
    public boolean addPhrases(File file) throws RemoteException {
        try {
            return phraseManager.addPhrases(file);
        }catch (IOException e){
            return false;
        }
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
    public String checkRecordPlayer() throws RemoteException {
        return monitoringManager.bestStatsUsers();
    }

    @Override
    public String checkPerPlayer(String nickname) throws RemoteException {
        return monitoringManager.perPlayerStats(nickname);
    }

    @Override
    public String bestMove() throws RemoteException {
        return monitoringManager.getBestMove();
    }

    @Override
    public int averageManches() throws RemoteException {
        return monitoringManager.averageMovesPerManches();
    }

    @Override
    public boolean resetPassword(Client c,String mail) throws RemoteException {
        return profileManager.resetPassword(mail);
    }
}
