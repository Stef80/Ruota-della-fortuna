package PROVATEST;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainS {
    public static void main(String[] args) throws Exception {
        Registry r = LocateRegistry.createRegistry(1099);
        ServerImpl ser = new ServerImpl();
        r.rebind("SERVER",ser);
        System.out.println("Server avviato");
    }
}
