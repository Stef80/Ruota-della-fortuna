package PROVATEST;

import PROVATEST.ClientImpl;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainC {
    public static void main(String[] args) throws Exception {
        Registry r = LocateRegistry.getRegistry(1099);
        ClientImpl client = new ClientImpl();
        Server server = (Server)r.lookup("SERVER");

        RemoteObj remObj = server.creaObj(client);
        remObj.notificaClient();
    }
}
