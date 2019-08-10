package PROVATEST;

import PROVATEST.RemoteObj;
import PROVATEST.RemoteObjImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
    public ServerImpl() throws RemoteException{}

    public RemoteObj creaObj(Client c)throws RemoteException {
        return new RemoteObjImpl(c);
    }
}
