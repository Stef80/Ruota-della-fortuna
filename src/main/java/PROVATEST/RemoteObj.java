package PROVATEST;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObj extends Remote {
    public void notificaClient() throws RemoteException;

}
