package serverRdF;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//TEMPORANEO

public class MainC {
    public static void main(String[] args) throws Exception{
        ServerImplementation server = new ServerImplementation(null,null);
        Registry r = LocateRegistry.createRegistry(1099);
        r.bind("SERVER", server);
    }
}
