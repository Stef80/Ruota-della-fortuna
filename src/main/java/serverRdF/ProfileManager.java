package serverRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.UsersDTO;
import serverRdF.emailRdF.EmailManager;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Questa classe si occupa della modifica dei dati di uno specifico utente
 */
public class ProfileManager {
    private DBManager dbManager;
    private EmailManager emailManager;
    private static ProfileManager profileManager = null;

    private ProfileManager(DBManager dbmng, EmailManager emailManager){
        dbManager = dbmng;
        this.emailManager = emailManager;
    }

    /**
     * @param dbmng il riferimento a {@link DBManager}
     * @return Il riferimento al singleton di {@link ProfileManager}
     */
    public static ProfileManager createProfileManager(DBManager dbmng,EmailManager emailManager){
        if(profileManager == null){
            profileManager = new ProfileManager(dbmng,emailManager);
            return profileManager;
        }else{
            return profileManager;
        }
    }

    public boolean changeName(String name, String idUser) throws SQLException {
            UsersDTO user = dbManager.getUserById(idUser);
            user.setName(name);
            return dbManager.updateUser(user);
    }

    public boolean changeSurname(String surname, String idUser) throws SQLException {
        UsersDTO user = dbManager.getUserById(idUser);
        user.setSurname(surname);
        return dbManager.updateUser(user);
    }

    public boolean changeNickname(String nickname, String idUser) throws SQLException {
        UsersDTO user = dbManager.getUserById(idUser);
        user.setNickname(nickname);
        return dbManager.updateUser(user);
    }

    public boolean changePassword(String password, String idUser) throws SQLException {
        UsersDTO user = dbManager.getUserById(idUser);
        user.setPassword(password);
        return dbManager.updateUser(user);
    }
}
