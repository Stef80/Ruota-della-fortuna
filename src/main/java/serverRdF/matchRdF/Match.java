package serverRdF.matchRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.PhrasesDTO;
import serverRdF.dbComm.UsersDTO;
import serverRdF.emailRdF.EmailManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    //nell'array non sono considerati gli spazi.
    private boolean[] phraseStatus;


    public Match(String id, LocalDateTime localDateTime, DBManager db, EmailManager email) throws RemoteException {
        this.id = id;
        onGoing = false;
        dbManager = db;
        emailmng = email;
        manche = new Manche(dbManager, id, localDateTime);
        players = new ArrayList<Player>();
        observers = new ArrayList<Client>();
        turn = -1;
        firstTurn = true;
        creationTime = localDateTime;
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

        startTurn(turn);
    }


    private void startTurn(int turn) throws RemoteException{
        Player activePlayer = players.get(turn);
        for(Client c : observers){
            try {
                c.notifyNewTurn(activePlayer.getNickname());
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try {
                if (p.equals(activePlayer)) {
                    p.getClient().notifyYourTurn();
                    p.getClient().notifyNewTurn(p.getNickname());
                } else {
                    p.getClient().notifyNewTurn(activePlayer.getNickname());
                }
            }catch(RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    public void giveSolution(String solution) throws RemoteException {
    }

    /**
     * Questo metodo permette l'inizio della partita
     *
     * @throws RemoteException
     */
    public void startMatch() throws RemoteException {
        Random rnd = new Random();
        onGoing = true;
        try {
            String idPlayer1 = players.get(0).getIdPlayer();
            String idPlayer2 = players.get(1).getIdPlayer();
            String idPlayer3 = players.get(2).getIdPlayer();

            List<PhrasesDTO> phrases = dbManager.get5Phrases(idPlayer1, idPlayer2, idPlayer3);

            if(phrases == null){
                try {
                    for (Client c : observers) {
                        try {
                            c.notifyMatchAbort("Partita annullata: non ci sono abbastanza frasi");
                        }catch(RemoteException e){
                            leaveMatchAsObserver(c);
                        }
                    }
                    for (Player p : players) {
                        try {
                            p.getClient().notifyMatchAbort("Partita annullata: non ci sono abbastanza frasi");
                        }catch(RemoteException er){
                            players.remove(p);
                        }
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
            }else {
                manche.setNumManche(1);
                PhrasesDTO newPhrase = manche.getCurrentPhrase();
                resetPhraseStatus(newPhrase.getPhrase());
                for (Client c : observers) {
                    try {
                        c.notifyMatchStart();
                        c.setNewPhrase(newPhrase.getTheme(), newPhrase.getPhrase());
                        dbManager.addMancheJoiner(id, manche.getNumManche(), c.getId(), true);
                    }catch(RemoteException e){
                        leaveMatchAsObserver(c);
                    }
                }
                for (Player p : players) {
                    try {
                        p.getClient().notifyMatchStart();
                        p.getClient().setNewPhrase(newPhrase.getTheme(), newPhrase.getPhrase());
                        dbManager.addMancheJoiner(id, manche.getNumManche(), p.getIdPlayer(), false);
                    }catch(RemoteException e){
                        leaveMatchAsPlayer(p);
                    }
                }

                turn = rnd.nextInt(3);
                nextTurn();
            }
        }catch(SQLException|RemoteException e) {
            try {
                for (Client c : observers) {
                    try {
                        c.notifyMatchAbort("Partita annullata: errore di comunicazione con il server");
                    }catch(RemoteException d){
                        leaveMatchAsObserver(c);
                    }
                }
                for (Player p : players) {
                    try {
                        p.getClient().notifyMatchAbort("Partita annullata: errore di comunicazione con il server");
                    }catch(RemoteException ex){
                        players.remove(p);
                    }
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

    private void resetPhraseStatus(String phrase){
        StringTokenizer st = new StringTokenizer(phrase);
        int length = 0;

        while(st.hasMoreTokens()){
            String s = st.nextToken();
            for(int j=0; j<s.length(); j++){
                length++;
            }
        }
        this.phraseStatus = new boolean[length];
        for(int k=0; k<phraseStatus.length; k++){
            phraseStatus[k] = false;
        }
    }

    /**
     * Questo metodo conclude la manche
     *
     * @param winner il vincitore della manche o <code>null</code> se non c'è stato alcun vincitore
     * @throws RemoteException
     */
    public void endManche(Player winner) throws RemoteException {
       manche.endManche(winner);
       if(winner != null) {
           for (Player p : players) {
               try {
                   if (p.equals(winner)) {
                       p.addPoints(p.getPartialPoints());
                       p.partialPointsToZero();
                       p.getClient().notifyMancheVictory();
                   } else {
                       p.partialPointsToZero();
                       p.getClient().notifyMancheResult(winner.getNickname());
                   }
               }catch(RemoteException e){
                   leaveMatchAsPlayer(p);
               }
           }

           if(manche.getNumManche() > 5) {
               endMatch(true);
               return;
           }else {
               PhrasesDTO newPhrase = manche.getCurrentPhrase();
               resetPhraseStatus(newPhrase.getPhrase());
               for (Client c : observers) {
                   try {
                       c.notifyMancheResult(winner.getNickname());
                       c.notifyNewManche(manche.getNumManche());
                       c.setNewPhrase(newPhrase.getTheme(), newPhrase.getPhrase());
                       dbManager.addMancheJoiner(id, manche.getNumManche(), c.getId(), true);
                   }catch(RemoteException e){
                       leaveMatchAsObserver(c);
                   }
               }
               for (Player p : players) {
                   try {
                       p.getClient().notifyNewManche(manche.getNumManche());
                       p.getClient().setNewPhrase(newPhrase.getTheme(), newPhrase.getPhrase());
                       dbManager.addMancheJoiner(id, manche.getNumManche(), p.getIdPlayer(), false);
                   }catch(RemoteException e){
                       leaveMatchAsPlayer(p);
                   }
               }
               nextTurn();
           }
       }else{
           endMatch(false);
       }
    }

    /**
     * Conclude il match
     *
     * @param isThereAWinner <code>true</code> se il match si è concluso dopo la conclusione della quinta manche, portando ad un vincitore, <code>false</code> altrimenti
     * @throws RemoteException
     */
    public void endMatch(boolean isThereAWinner) throws RemoteException {
        MatchManager.deleteMatch(id);
        if(isThereAWinner){
            Player winner = null;
            int maxPoint = 0;
            for(Player p : players){
                if(p.getPoints() > maxPoint){
                    maxPoint = p.getPoints();
                    winner = p;
                }
            }

            for(Client c : observers){
                try{
                    c.notifyEndMatch(winner.getNickname());
                }catch(RemoteException e){
                    leaveMatchAsObserver(c);
                }
            }

            for(Player p : players){
                try{
                    if(p.equals(winner)) {
                        p.getClient().notifyMatchWin();
                    }else{
                        p.getClient().notifyEndMatch(winner.getNickname());
                    }
                }catch(RemoteException e){
                    leaveMatchAsPlayer(p);
                }
            }

            dbManager.addMatchWinner(id, winner.getIdPlayer(), maxPoint);
        }
        UnicastRemoteObject.unexportObject(this, true);
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
        if(onGoing){
            dbManager.addMancheJoiner(id, manche.getNumManche(), c.getId(), true);
        }
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
            try {
                if (p.getClient().equals(c)) {
                    players.remove(p);
                } else {
                    p.getClient().notifyLeaver(name);
                }
            }catch(RemoteException e){
                players.remove(p);
            }
        }
        for (Client client : observers) {
            try {
                client.notifyLeaver(name);
            }catch(RemoteException e){
                leaveMatchAsObserver(client);
            }
        }
        endManche(null);
        endMatch(false);
    }

    private void leaveMatchAsPlayer(Player player) throws RemoteException {
        String name = player.getNickname();
        for (Player p : players) {
            try {
                if (p.getClient().equals(player)) {
                    players.remove(p);
                } else {
                    p.getClient().notifyLeaver(name);
                }
            }catch(RemoteException e){
                players.remove(p);
            }
        }
        for (Client client : observers) {
            try {
                client.notifyLeaver(name);
            }catch(RemoteException e){
                leaveMatchAsObserver(client);
            }
        }
        endManche(null);
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

    public void askNotify(Client c) throws RemoteException {
        if(onGoing){
            for(int i=0; i<players.size(); i++){
                c.notifyPlayerStats(i+1, players.get(i).getNickname(), players.get(i).getPartialPoints(), players.get(i).getPoints());
                c.setNewPhrase(manche.getCurrentPhrase().getTheme(), manche.getCurrentPhrase().getPhrase());
                c.updatePhrase(phraseStatus);
            }
        }else{
            for(int i=0; i<players.size(); i++) {
                c.notifyPlayerStats(i + 1, players.get(i).getNickname(), 0, 0);
            }
        }
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