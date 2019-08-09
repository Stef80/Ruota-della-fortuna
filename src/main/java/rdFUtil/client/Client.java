package rdFUtil.client;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    public String getNickname() throws RemoteException;

    public String getId() throws RemoteException;

    public void setNickname() throws RemoteException;

    public void setId() throws RemoteException;

    public void notifyServerError() throws RemoteException;

    public void notifyRegistrationConfirmed() throws RemoteException;

    public void notifyTooManyPlayers() throws RemoteException;

    public void notifyLeaver(String nickname) throws RemoteException;
}
