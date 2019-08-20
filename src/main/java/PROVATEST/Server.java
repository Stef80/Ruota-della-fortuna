package PROVATEST;

import PROVATEST.RemoteObj;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    public RemoteObj creaObj(Client c) throws RemoteException;
}
