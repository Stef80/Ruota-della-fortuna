package server.registration;

import util.client.Client;
import util.logging.User;
import server.ServerImplementation;
import server.dbComm.DBManager;

import java.rmi.RemoteException;


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

    /**
     * Attende 10 minuti. Se non viene interrotto annulla la registrazione, altrimenti la completa
     */
    public void run() {
        int tenMininSec = 600000;
        try {
            sleep(tenMininSec);
        } catch (InterruptedException e) {
            boolean bool = dbManager.addUser(user, admin);
            if (!bool) {
                ServerImplementation.serverError(client);
                e.printStackTrace();
            }
            try {
                client.notifyRegistrationResult(true);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
}
