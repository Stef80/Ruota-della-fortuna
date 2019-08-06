package serverRdF;

import rdFUtil.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteMatch extends Remote {
    public int wheelSpin() throws RemoteException;

    public void giveConsonant(char letter, int amount) throws RemoteException;

    public void giveVocal(char letter) throws RemoteException;

    public void jolly() throws RemoteException;

    public void nextTurn() throws RemoteException;

    public void giveSolution(String solution) throws RemoteException;

    public void startMatch() throws RemoteException;

    public void endManche(Player winner) throws RemoteException;

    public void endMatch() throws RemoteException;

    public void addPlayer(Client c) throws RemoteException;

    public void addObserver(Client c) throws RemoteException;

    public void leaveMatch() throws RemoteException;

    public void askNotify() throws RemoteException;
}
