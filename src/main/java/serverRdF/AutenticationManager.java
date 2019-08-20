package serverRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.Login;
import serverRdF.dbComm.DBManager;

import java.sql.SQLException;


/**
 * Questa classe gestisce le autenticazioni.
 */
public class AutenticationManager {
    private DBManager dbManager;
    private static AutenticationManager autenticationManager;

    private AutenticationManager(DBManager dbmng){
        dbManager = dbmng;
    }

    /**
     *
     * @param dbManager il riferimento al gestore del db
     * @return autenticationManager, il singleton della classe
     */
    public static AutenticationManager createAutenticationManager(DBManager dbManager){
        if (autenticationManager == null) {
            autenticationManager = new AutenticationManager(dbManager);
            return autenticationManager;
        } else
            return autenticationManager;
    }


    /**
     *
     * @param form contenente i dati necessari all'autenticazione
     * @param c il riferimento al client
     * @return true se l'autenticazione è andata a buon fine, false altrimenti
     *
     * Nel caso in cui ci siano problemi con la connessione al server, il client viene notificato
     */
    public int signIn(Login form, Client c, boolean admin) {
        String email = form.getEmail();
        String password = form.getPasswordC();
        try {
            return dbManager.checkLogin(email, password, admin);
        } catch (SQLException e) {
            ServerImplementation.serverError(c);
            return -1;
        }
    }
}
