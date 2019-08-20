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
    private boolean admin;

    public WaitingThread(Client c, DBManager dbManager, User id, boolean admin) {
        client = c;
        this.dbManager = dbManager;
        user = id;
        this.admin = admin;
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
                boolean bool = dbManager.addUser(user, admin);
                if (!bool) {
                    ServerImplementation.serverError(client);
                }
            } catch (RemoteException | SQLException exc) {
                ServerImplementation.serverError(client);
            }
        }
    }
}
