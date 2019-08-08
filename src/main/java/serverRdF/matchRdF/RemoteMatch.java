package serverRdF.matchRdF;

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

    public void endMatch(boolean isThereAWinner) throws RemoteException;

    public boolean addPlayer(Client c) throws RemoteException;

    public void addObserver(Client c) throws RemoteException;

    public void leaveMatchAsPlayer(Client c) throws RemoteException;

    public void leaveMatchAsObserver(Client c) throws RemoteException;

    public void askNotify() throws RemoteException;
}
