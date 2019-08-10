package PROVATEST;

import PROVATEST.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    public ClientImpl() throws RemoteException{}

    public void stamp()throws RemoteException{
        System.out.println("OKKKKK");
    }
}
