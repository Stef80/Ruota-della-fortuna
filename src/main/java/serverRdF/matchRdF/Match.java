package serverRdF.matchRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private LocalDateTime creationTime;


    public Match(String id, LocalDateTime localDateTime) throws RemoteException{
        this.id = id;
        onGoing = false;
        manche = new Manche();
        players = new ArrayList<Player>();
        observers = new ArrayList<Client>();
        turn = -1;
        firstTurn = true;
        creationTime = localDateTime;
    }

    public int wheelSpin() throws RemoteException{
        return 0;
    }

    public void giveConsonant(char letter, int amount) throws RemoteException{}

    public void giveVocal(char letter) throws RemoteException{}

    public void jolly() throws RemoteException{}

    public void nextTurn() throws RemoteException{
        if(turn==2)
            turn=0;
        else
            ++turn;
    }

    public void giveSolution(String solution) throws RemoteException{}

    public void startMatch() throws RemoteException{}

    public void endManche(Player winner) throws RemoteException{}

    public void endMatch() throws RemoteException{}

    /**
     * @param c
     * @return full full=true se la partita è piena rendendo impossibile la partecipazione, full=false altrimenti
     * @throws RemoteException
     */
    public boolean addPlayer(Client c) throws RemoteException{
        boolean full;
        if(players.size()>=3)
            full=true;
        else {
            players.add(new Player(c));
            full=false;
        }
        return full;
    }

    public void addObserver(Client c) throws RemoteException{
        observers.add(c);
    }

    public void leaveMatch() throws RemoteException{}

    public void askNotify() throws RemoteException{}

    public Match() throws RemoteException{}

    public MatchData createMatchData(){
        MatchData result = new MatchData();

        String noName = "--";
        result.setPlayer1(players.get(0).getNickname());
        if(players.get(1) != null){
            result.setPlayer2(players.get(1).getNickname());
        }else{
            result.setPlayer2(noName);
        }
        if(players.get(2) != null){
            result.setPlayer3(players.get(2).getNickname());
        }else{
            result.setPlayer3(noName);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
        result.setDate(dtf.format(creationTime));
        dtf = DateTimeFormatter.ofPattern("HH:mm");
        result.setTime(dtf.format(creationTime));

        result.setOnGoing(onGoing);
        result.setNumManche(manche.getNumManche());

        return result;
    }

    //TODO
}