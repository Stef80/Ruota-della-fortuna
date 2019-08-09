package serverRdF.registrationRdF;

import rdFUtil.client.Client;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;

import java.rmi.RemoteException;

public class WaitingThread extends Thread {
    private Client client;
    private DBManager dbManager;
    private String idUser;

    public WaitingThread(Client c, DBManager dbManager, String id){
        client = c;
        this.dbManager = dbManager;
        idUser = id;
    }

    public void run(){
        int tenMininSec = 600000;
        try{
            sleep(tenMininSec);
            boolean bool = dbManager.deleteUser(idUser);
            if(!bool){
                ServerImplementation.serverError(client);
            }
            try{
                client.notifyRegistrationResult(false);
            }catch(RemoteException exc){
                ServerImplementation.serverError(client);
            }
        }catch(InterruptedException e){
            try{
                client.notifyRegistrationResult(true);
            }catch(RemoteException exc){
                ServerImplementation.serverError(client);
            }
        }
    }
}
