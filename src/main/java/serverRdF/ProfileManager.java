package serverRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.CryptPassword;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.UsersDTO;
import serverRdF.emailRdF.EmailManager;

import java.util.Random;

/**
 * Questa classe si occupa della modifica dei dati di uno specifico utente
 */
public class ProfileManager {
    private DBManager dbManager;
    private EmailManager emailManager;
    private static ProfileManager profileManager = null;

    private ProfileManager(DBManager dbmng, EmailManager emailManager) {
        dbManager = dbmng;
        this.emailManager = emailManager;
    }

    /**
     * @param dbmng        il riferimento a {@link DBManager}
     * @param emailManager //TODO
     * @return Il riferimento al singleton di {@link ProfileManager}
     */
    public static ProfileManager createProfileManager(DBManager dbmng, EmailManager emailManager) {
        if (profileManager == null) {
            profileManager = new ProfileManager(dbmng, emailManager);
            return profileManager;
        } else {
            return profileManager;
        }
    }

    public boolean changeName(String name, String idUser) {
        UsersDTO user = dbManager.getUserById(idUser);
        user.setName(name);
        return dbManager.updateUser(user);
    }

    public boolean changeSurname(String surname, String idUser) {
        UsersDTO user = dbManager.getUserById(idUser);
        user.setSurname(surname);
        return dbManager.updateUser(user);
    }

    public boolean changeNickname(String nickname, String idUser) {
        UsersDTO user = dbManager.getUserById(idUser);
        user.setNickname(nickname);
        return dbManager.updateUser(user);
    }

    public boolean changePassword(String password, String idUser){
        UsersDTO user = dbManager.getUserById(idUser);
        user.setPassword(password);
        String email = user.getEmail();
        String sub = "RdF: modifica della password";
        String txt = user.getName()+"! \n La informiamo che la sua password e' stata modificata con successo.";
        emailManager.sendEmail(email,sub,txt);
        return dbManager.updateUser(user);
    }

    public boolean resetPassword(String email){
        UsersDTO user = dbManager.getUserByEmail(email);
        if(user != null) {
            String password = generateRandomPassword();
            String sub = "RdF: reset della password";
            String txt = user.getName()+"!\nLa password del suo account e' stata resettata con successo. Usa questa password per accedere: " + password +".\nLe consigliamo di modificarla al piu' presto";
            emailManager.sendEmail(email,sub,txt);
            user.setPassword(CryptPassword.encrypt(password));
            dbManager.updateUser(user);
            return true;
        }else{
            return false;
        }
    }

    private static String generateRandomPassword(){
        Random rnd = new Random();
        String result = "P";
        for(int i=0; i<8; i++) {
            int chars = rnd.nextInt(3);
            switch (chars) {
                case 0:
                    result += rnd.nextInt(10);
                    break;
                case 1:
                    result += (char)(rnd.nextInt(26) + 65);
                    break;
                case 2:
                    result += (char)(rnd.nextInt(26) + 97);
                    break;
            }
        }
        return result;
    }
}
