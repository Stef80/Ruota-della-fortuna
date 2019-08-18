package serverRdF.matchRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.PhrasesDTO;
import serverRdF.dbComm.UsersDTO;
import serverRdF.emailRdF.EmailManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
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
    private DBManager dbManager;
    private EmailManager emailmng;


    public Match(String id, LocalDateTime localDateTime, DBManager db, EmailManager email) throws RemoteException {
        this.id = id;
        onGoing = false;
        manche = new Manche();
        players = new ArrayList<Player>();
        observers = new ArrayList<Client>();
        turn = -1;
        firstTurn = true;
        creationTime = localDateTime;
        dbManager = db;
        emailmng = email;
    }

    public int wheelSpin() throws RemoteException {
        return 0;
    }

    public void giveConsonant(char letter, int amount) throws RemoteException {
    }

    public void giveVocal(char letter) throws RemoteException {
    }

    public void jolly() throws RemoteException {
    }

    /**
     * Questo metodo permette di conoscere il concorrente a cui appartiene il nuovo turno di gioco
     *
     * @throws RemoteException
     */
    public void nextTurn() throws RemoteException {
        if (turn == 2)
            turn = 0;
        else
            ++turn;
    }

    public void giveSolution(String solution) throws RemoteException {
    }

    public void startMatch() throws RemoteException {
        onGoing = true;
        try {
            String idPlayer1 = players.get(0).getNickname();
            String idPlayer2 = players.get(1).getNickname();
            String idPlayer3 = players.get(2).getNickname();

            List<PhrasesDTO> phrases = dbManager.get5Phrases(idPlayer1, idPlayer2, idPlayer3);

            if(phrases.size() <= 5){
                try {
                    for (Client c : observers) {
                        c.notifyMatchAbort("Partita annullata: non ci sono abbastanza frasi");
                    }
                    for (Player p : players) {
                        p.getClient().notifyMatchAbort("Partita annullata: non ci sono abbastanza frasi");
                    }
                    MatchManager.deleteMatch(id);
                    boolean bool = dbManager.deleteMatch(id);
                    if (!bool) {
                        throw new SQLException();
                    }
                    List<UsersDTO> admins = dbManager.getAllAdmin();
                    String email = "";
                    String obj = "Mancanza di frasi in DBRdF";
                    String txt = "Una o piu' partite sono state annullate a causa della mancanza di frasi nel database. Per favore aggiungere nuove frasi attraverso la piattaforma AdminRdf / ServerRdF";
                    for(UsersDTO admin : admins) {
                        email = admin.getEmail();
                        emailmng.sendEmail(email, obj, txt);
                    }
                }catch(SQLException|RemoteException e){
                    ServerImplementation.serverError(null);
                }
                //TODO
            }


        }catch(SQLException e) {
            try {
                for (Client c : observers) {
                    c.notifyMatchAbort("Partita annullata: errore di comunicazione con il server");
                }
                for (Player p : players) {
                    p.getClient().notifyMatchAbort("Partita annullata: errore di comunicazione con il server");
                }
                MatchManager.deleteMatch(id);
                boolean bool = dbManager.deleteMatch(id);
                if (!bool) {
                    throw new SQLException();
                }
            } catch (SQLException | RemoteException ex) {
                ServerImplementation.serverError(null);
            }
        }
    }

    /**
     * Questo metodo conclude la manche se ci e' stato un vincitore
     *
     * @param winner il vincitore della manche
     * @throws RemoteException
     */
    public void endManche(Player winner) throws RemoteException {
        //TODO
    }

    /**
     * Questo metodo conclude la manche se non ci e' stato un vincitore
     *
     * @throws RemoteException
     */
    public void endManche() throws RemoteException {
        //TODO
    }

    /**
     * @param isThereAWinner <code>true</code> se il match si è concluso dopo la conclusione della quinta manche, portando ad un vincitore, <code>false</code> altrimenti
     * @throws RemoteException
     */
    public void endMatch(boolean isThereAWinner) throws RemoteException {
        //TODO
    }

    /**
     * @param c riferimento al concorrente
     * @return full <code>true</code> se la partita è piena rendendo impossibile la partecipazione, <code>false</code> altrimenti
     * @throws RemoteException
     */
    public boolean addPlayer(Client c) throws RemoteException {
        boolean full;
        if (players.size() >= 3)
            full = true;
        else {
            players.add(new Player(c));
            full = false;
            if(players.size() == 3){
                startMatch();
            }
        }
        return full;
    }

    /**
     * @param c riferimento all'osservatore
     * @throws RemoteException
     */
    public void addObserver(Client c) throws RemoteException {
        observers.add(c);
    }

    /**
     * Il metodo si occupa di notificare ai partecipanti e ai giocatori l'abbandono di un giocatore e porta alla conclusione del match
     *
     * @param c Il client che abbandona il match
     * @throws RemoteException
     */
    public void leaveMatchAsPlayer(Client c) throws RemoteException {
        String name = c.getNickname();
        for (Player p : players) {
            if (p.getClient().equals(c)) {
                players.remove(p);
            } else {
                p.getClient().notifyLeaver(name);
            }
        }
        for (Client client : observers) {
            client.notifyLeaver(name);
        }
        endManche();
        endMatch(false);
    }

    /**
     * Il suo abbandono non viene notificato
     *
     * @param c Il riferimento al client che smette di guardare la partita
     * @throws RemoteException
     */
    public void leaveMatchAsObserver(Client c) throws RemoteException {
        observers.remove(c);
    }

    public void askNotify() throws RemoteException {
    }

    @Override
    public String getMatchId() throws RemoteException {
        return id;
    }

    public Match() throws RemoteException {
    }

    /**
     * @return un oggetto di tipo MatchData contenente tutte le informazioni del match necessarie a MatchVisualizer
     */
    public MatchData createMatchData() {
        MatchData result = new MatchData();

        String noName = "--";
        result.setPlayer1(players.get(0).getNickname());
        if (players.get(1) != null) {
            result.setPlayer2(players.get(1).getNickname());
        } else {
            result.setPlayer2(noName);
        }
        if (players.get(2) != null) {
            result.setPlayer3(players.get(2).getNickname());
        } else {
            result.setPlayer3(noName);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
        result.setDate(dtf.format(creationTime));
        dtf = DateTimeFormatter.ofPattern("HH:mm");
        result.setTime(dtf.format(creationTime));

        result.setOnGoing(onGoing);
        result.setNumManche(manche.getNumManche());
        result.setIdMatch(id);

        return result;
    }

    //TODO
}