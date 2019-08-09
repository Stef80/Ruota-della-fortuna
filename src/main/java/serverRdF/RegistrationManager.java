package serverRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.User;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.UsersDTO;

/**
 * Questa classe gestisce la registrazione dell'utente. I metodi checkEmail e checkNickname permettono di allegerire i controlli sul metodo principale della classe (signUp)
 */
public class RegistrationManager {
    private DBManager dbManager;
    private static RegistrationManager registrationManager = null;

    private RegistrationManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }


    /**
     * @param dbManager il riferimento al manager del db
     * @return registrationManager il singleton della classe.
     */
    public static RegistrationManager createRegistrationManager(DBManager dbManager) {
        if (registrationManager == null) {
            registrationManager = new RegistrationManager(dbManager);
            return registrationManager;
        } else
            return registrationManager;
    }

    /**
     * Registra l'utente nel database. Nel caso non riesca notifica al client l'errore
     *
     * @param form contenente i dati necessari alla registrazione
     * @param c    il riferimento al client
     */
    public void signUp(User form, Client c) {
        boolean bool = dbManager.addUser(form);
        if (!bool) {
            ServerImplementation.serverError(c);
        }

        //TODO la parte di invio della email
    }

    /**
     * @param email L'indirizzo email da controllare
     * @return <code>true</code> se l'indirizzo email non è stato già utilizzato, <code>false</code>false altrimenti
     */
    public boolean checkEmail(String email) {
        UsersDTO user = dbManager.getUser(true, email);
        if (user != null) {
            return false;
        } else return true;
    }

    /**
     * @param nickname il nickname da controllare
     * @return true se il nickname non è stato già utilizzato, false altrimenti
     */
    public boolean checkNickname(String nickname) {
        UsersDTO user = dbManager.getUser(false, nickname);
        if (user != null) {
            return false;
        } else return true;
    }
}
