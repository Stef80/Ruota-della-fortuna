package serverRdF;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static final String HOSTNAME = "ServerRdF";
    public static final int PORT = 1099;

    public static void main(String[] args) {
        /*
        try {
            InetSocketAddress address = new InetSocketAddress(HOSTNAME,PORT);
            System.out.println(address.getHostName() + address.getAddress().toString());
            Registry r;
            if(LocateRegistry.getRegistry(HOSTNAME,PORT) == null){
                r = LocateRegistry.createRegistry(PORT);
            }else{
                r = LocateRegistry.getRegistry(HOSTNAME,PORT);
            }
            //TODO
        } catch (RemoteException e) {
            ServerImplementation.serverError(null);
        }
         */
        try {
            InetAddress addr = Inet4Address.getLocalHost();
            byte[] bb = addr.getAddress();
            for(Byte b : bb){
                System.out.print(b);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
