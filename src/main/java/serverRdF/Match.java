package serverRdF;

import rdFUtil.client.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Match extends UnicastRemoteObject implements RemoteMatch {

    private List<Player> players;
    private List<Client> observers;
    private String id;
    private boolean onGoing;
    private Manche manche;
    private int turn;
    private boolean firstTurn;


    public Match(String id) throws RemoteException{
        this.id = id;
        onGoing = false;
        manche = new Manche();
        players = new ArrayList<Player>();
        observers = new ArrayList<Client>();
        turn = -1;
        firstTurn = true;
    }

    public int wheelSpin() throws RemoteException{}

    public void giveConsonant(char letter, int amount) throws RemoteException{}

    public void giveVocal(char letter) throws RemoteException{}

    public void jolly() throws RemoteException{}

    public void nextTurn() throws RemoteException{}

    public void giveSolution(String solution) throws RemoteException{}

    public void startMatch() throws RemoteException{}

    public void endManche(Player winner) throws RemoteException{}

    public void endMatch() throws RemoteException{}

    public void addPlayer(Client c) throws RemoteException{}

    public void addObserver(Client c) throws RemoteException{}

    public void leaveMatch() throws RemoteException{}

    public void askNotify() throws RemoteException{}

    public Match() throws RemoteException{}

    //TODO
}
