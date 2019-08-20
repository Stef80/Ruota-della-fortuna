package PROVATEST;

import PROVATEST.RemoteObj;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjImpl extends UnicastRemoteObject implements RemoteObj {
    private Client client;

    public RemoteObjImpl(Client c) throws RemoteException{
        client = c;
    }

    public void notificaClient() throws RemoteException{
        System.out.println("Usato");
        client.stamp();
    }
}
