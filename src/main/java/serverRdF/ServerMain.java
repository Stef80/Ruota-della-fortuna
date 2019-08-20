package serverRdF;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostName = addr.getHostName();
            System.out.println(hostName);
            Registry r;
            if(LocateRegistry.getRegistry(1099) == null){
                r = LocateRegistry.createRegistry(1099);
            }else{
                r = LocateRegistry.getRegistry(1099);
            }
            //TODO
        } catch (UnknownHostException | RemoteException e) {
            ServerImplementation.serverError(null);
        }
    }
}
