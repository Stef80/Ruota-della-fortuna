package serverRdF.registrationRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.User;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;

import java.rmi.RemoteException;
import java.sql.SQLException;


/**
 * Classe che aspetta per dieci minuti che venga inserita l'OTP esatta. In caso contrario la registrazione viene annullata.
 */
public class WaitingThread extends Thread {
    private Client client;
    private DBManager dbManager;
    private User user;

    public WaitingThread(Client c, DBManager dbManager, User id) {
        client = c;
        this.dbManager = dbManager;
        user = id;
    }

    public void run() {
        int tenMininSec = 600000;
        try {
            sleep(tenMininSec);
            try {
                client.notifyRegistrationResult(false);
            } catch (RemoteException exc) {
                ServerImplementation.serverError(client);
            }
        } catch (InterruptedException e) {
            try {
                client.notifyRegistrationResult(true);
                boolean bool = dbManager.addUser(user, false); //todo da inserire nel costruttore il boolean isAdmin
                if (!bool) {
                    ServerImplementation.serverError(client);
                }
            } catch (RemoteException | SQLException exc) {
                ServerImplementation.serverError(client);
            }
        }
    }
}
