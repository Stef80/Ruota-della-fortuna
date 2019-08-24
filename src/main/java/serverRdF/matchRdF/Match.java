package serverRdF.matchRdF;

import rdFUtil.MatchData;
import rdFUtil.client.Client;
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
    //nell'array non sono considerati gli spazi o i caratteri speciali.
    private boolean[] phraseStatus;
    private MoveTimer timer = null;
    private boolean spinnedWheel = false;

    public Match() throws RemoteException {

    }

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

    @Override
    public int wheelSpin() throws RemoteException {
        Player activePlayer = players.get(turn);

        if(timer.isThisForJolly() || timer.isThisForSolution() || timer.isThisForVocal()){
            errorInTurn(false,false);
            return 0;
        }else {
            firstTurn = false;
            timer.interrupt();
            wheelResult("GIRA...");
            int j = 0;
            for(int i=0; i<1000000000; i++){
                j = j*5;
            }
            spinnedWheel = true;
            Random rnd = new Random();
            int result = rnd.nextInt(24);
        /*
        0 = passa
        1 = jolly
        2 - 6 = 400
        7 - 8 = perde
        9 - 12 = 500
        13 - 18 = 300
        19 - 21 = 600
        22 = 1000
        23 = 700
         */
            switch (result) {
                case 0:
                    wheelResult("PASSA");
                    manche.getTurns().addMove(activePlayer.getIdPlayer(), "passa", 0);
                    errorInTurn(true,true);
                    return 0;
                case 1:
                    wheelResult("JOLLY");
                    manche.getTurns().addMove(activePlayer.getIdPlayer(), "jolly", -1);
                    activePlayer.addJolly();
                    for (Client c : observers) {
                        try {
                            Player p = null;
                            for(int i=0; i<3; i++) {
                                p = players.get(i);
                                c.notifyPlayerStats(i, p.getNickname(), 0, p.getPoints(),p.getNumJolly());
                            }
                        }catch(RemoteException e){
                            leaveMatchAsObserver(c);
                        }
                    }
                    for (Player p : players) {
                        try {
                            Player pl = null;
                            for(int i=0; i<3; i++) {
                                pl = players.get(i);
                                p.getClient().notifyPlayerStats(i, pl.getNickname(), 0, pl.getPoints(), pl.getNumJolly());
                            }
                        }catch(RemoteException e){
                            leaveMatchAsPlayer(p);
                        }
                    }
                    startTurn(turn);
                    return 0;
                case 2: case 3: case 4: case 5: case 6:
                    wheelResult("400");
                    startTimer(5000, false,false,false);
                    return 400;
                case 7: case 8:
                    wheelResult("PERDE");
                    manche.getTurns().addMove(activePlayer.getIdPlayer(), "perde", 0);
                    for (Client c : observers) {
                        try {
                            Player p = null;
                            for(int i=0; i<3; i++) {
                                p = players.get(i);
                                c.notifyPlayerStats(i, p.getNickname(), 0, p.getPoints(), p.getNumJolly());
                            }
                        }catch(RemoteException e){
                            leaveMatchAsObserver(c);
                        }
                    }
                    for (Player p : players) {
                        try {
                            Player pl = null;
                            for(int i=0; i<3; i++) {
                                pl = players.get(i);
                                p.getClient().notifyPlayerStats(i, pl.getNickname(), 0, pl.getPoints(),pl.getNumJolly());
                            }
                        }catch(RemoteException e){
                            leaveMatchAsPlayer(p);
                        }
                    }
                    nextTurn();
                    return 0;
                case 9: case 10: case 11: case 12:
                    wheelResult("500");
                    startTimer(5000, false,false,false);
                    return 500;
                case 13: case 14: case 15: case 16: case 17: case 18:
                    wheelResult("300");
                    startTimer(5000, false,false, false);
                    return 300;
                case 19: case 20: case 21:
                    wheelResult("600");
                    startTimer(5000, false,false, false);
                    return 600;
                case 22:
                    wheelResult("1000");
                    startTimer(5000, false,false, false);
                    return 1000;
                case 23:
                    wheelResult("700");
                    startTimer(5000, false,false, false);
                    return 700;
                default:
                    return 0;
            }
        }
    }

    private void wheelResult(String result) throws RemoteException{
        for(Client c : observers){
            try{
                c.notifyWheelResult(result);
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try{
                p.getClient().notifyWheelResult(result);
            }catch (RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    /**
     * Metodo che notifica a tutti che il giocatore ha commesso un errore
     *
     * @throws RemoteException
     */
    private void notifyError() throws RemoteException{
        Player activePlayer = players.get(turn);
        for(Client c : observers){
            try {
                c.notifyPlayerError(activePlayer.getNickname());
            }catch (RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try{
                p.getClient().notifyPlayerError(activePlayer.getNickname());
            }catch (RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    @Override
    public void giveConsonant(String letter, int amount) throws RemoteException {
        Player activePlayer = players.get(turn);
        char vocal = letter.charAt(0);
        boolean isVocal = (vocal == 'A' || vocal == 'E' || vocal == 'I' || vocal == 'O' || vocal == 'U');
        if(firstTurn || !spinnedWheel || amount==0 || timer.isThisForJolly() || timer.isThisForSolution() || letter.length()>1 || isVocal || timer.isThisForVocal()){
            timer.interrupt();
            errorInTurn(true,false);
            return;
        }
        timer.interrupt();
        notifyLetterCall(letter);
        for(Client c : observers){
            try{
                c.updatePhrase(letter);
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try{
                p.getClient().updatePhrase(letter);
            }catch (RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
        String phrase = manche.getCurrentPhrase().getPhrase();
        StringTokenizer st = new StringTokenizer(phrase, " ',!?.:;\"/()\\^<>-+*");
        int j = 0;
        int counter = 0;
        while(st.hasMoreTokens()){
            String ss = st.nextToken();
            for(int i=0; i<ss.length(); i++){
                if(ss.charAt(i) == letter.charAt(0)){
                    if(phraseStatus[j] == false) {
                        phraseStatus[j] = true;
                        counter++;
                    }
                    j++;
                }else{
                    j++;
                }
            }
        }
        if(counter > 0){
            int result = counter * amount;
            manche.getTurns().addMove(activePlayer.getIdPlayer(), ""+letter, result);
            players.get(turn).updatePartialPoints(result);
            for(Client c : observers){
                try{
                    c.notifyPlayerStats(turn, activePlayer.getNickname(), activePlayer.getPartialPoints(), activePlayer.getPoints(), activePlayer.getNumJolly());
                }catch (RemoteException e){
                    leaveMatchAsObserver(c);
                }
            }
            for(Player p : players){
                try{
                    p.getClient().notifyPlayerStats(turn, activePlayer.getNickname(), activePlayer.getPartialPoints(), activePlayer.getPoints(), activePlayer.getNumJolly());
                }catch (RemoteException e){
                    leaveMatchAsPlayer(p);
                }
            }
            startTurn(turn);
        }else{
            manche.getTurns().addMove(activePlayer.getIdPlayer(), ""+letter, 0);
            errorInTurn(true,true);
        }
    }

    private void notifyLetterCall(String letter) throws RemoteException{
        Player activePlayer = players.get(turn);
        for(Client c : observers){
            try {
                c.notifyLetterCall(activePlayer.getNickname(), letter);
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try {
                if (p.equals(activePlayer)) {
                } else {
                    p.getClient().notifyLetterCall(activePlayer.getNickname(), letter);
                }
            }catch(RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }


    @Override
    public void askForVocal() throws RemoteException {
        if(firstTurn || timer.isThisForJolly() || players.get(turn).getPartialPoints()<1000){
            timer.interrupt();
            errorInTurn(true,false);
            return;
        }
        notifyVocalAsk();
        startTimer(10000, false, false, true);
    }

    private void notifyVocalAsk() throws RemoteException{
        Player activePlayer = players.get(turn);
        for(Client c : observers){
            try {
                c.notifyTryVocal(activePlayer.getNickname());
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try {
                if (p.equals(activePlayer)) {
                } else {
                    p.getClient().notifyTryVocal(activePlayer.getNickname());
                }
            }catch(RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    @Override
    public void giveVocal(String letter) throws RemoteException {
        Player activePlayer = players.get(turn);
        char vocal = letter.charAt(0);
        boolean isVocal = (vocal == 'A' || vocal == 'E' || vocal == 'I' || vocal == 'O' || vocal == 'U');
        if(firstTurn || timer.isThisForJolly() || timer.isThisForSolution() || letter.length()>1 || !isVocal || !timer.isThisForVocal()){
            timer.interrupt();
            errorInTurn(true,false);
            return;
        }
        timer.interrupt();
        notifyLetterCall(letter);
        for(Client c : observers){
            try{
                c.updatePhrase(letter);
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try{
                p.getClient().updatePhrase(letter);
            }catch (RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
        activePlayer.updatePartialPoints(-1000);
        String phrase = manche.getCurrentPhrase().getPhrase();
        StringTokenizer st = new StringTokenizer(phrase," ',!?.:;\"/()\\^<>-+*");
        int j = 0;
        int counter = 0;
        while(st.hasMoreTokens()){
            String ss = st.nextToken();
            for(int i=0; i<ss.length(); i++){
                if(ss.charAt(i) == letter.charAt(0)){
                    if(phraseStatus[j] == false) {
                        phraseStatus[j] = true;
                        counter++;
                    }
                    j++;
                }else{
                    j++;
                }
            }
        }
        for(Client c : observers){
            try{
                c.notifyPlayerStats(turn, activePlayer.getNickname(), activePlayer.getPartialPoints(), activePlayer.getPoints(), activePlayer.getNumJolly());
            }catch (RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try{
                p.getClient().notifyPlayerStats(turn, activePlayer.getNickname(), activePlayer.getPartialPoints(), activePlayer.getPoints(),activePlayer.getNumJolly());
            }catch (RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
        if(counter > 0){
            manche.getTurns().addMove(activePlayer.getIdPlayer(), ""+letter, -1);
            startTurn(turn);
        }else{
            manche.getTurns().addMove(activePlayer.getIdPlayer(), ""+letter, 0);
            errorInTurn(true,true);
        }

    }

    @Override
    public void jolly() throws RemoteException {
        Player activePlayer = players.get(turn);
        if(!timer.isThisForJolly() || timer.isThisForSolution() || timer.isThisForVocal()){
            timer.interrupt();
            errorInTurn(true, false);
            return;
        }else{
            timer.interrupt();
            manche.getTurns().getLastMove().setOutCome(-1);
            activePlayer.removeJolly();
            notifyJollyUsed();
            startTurn(turn);
        }
    }

    private void notifyJollyUsed() throws RemoteException{
        Player activePlayer = players.get(turn);
        for(Client c : observers){
            try {
                c.notifyJollyUsed(activePlayer.getNickname());
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try {
                if (p.equals(activePlayer)) {
                } else {
                    p.getClient().notifyJollyUsed(activePlayer.getNickname());
                }
            }catch(RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    /**
     * indica che e' stato commesso un errore e permette l'inizio di un nuovo turno o la giocata del jolly
     *
     * @param canJollyBeUsed <code>true</code> se il jolly può essere utilizzato, <code>false</code> altrimenti
     */
    void errorInTurn(boolean canJollyBeUsed, boolean moveDone) {
        Player activePlayer = players.get(turn);
        try {
            if (canJollyBeUsed) {
                notifyError();
                if (players.get(turn).getNumJolly() > 0) {
                    players.get(turn).getClient().askForJolly();
                    startTimer(5000, true, false, false);
                }else{
                    if(moveDone)
                        manche.getTurns().getLastMove().setOutCome(0);
                    else
                        manche.getTurns().addMove(activePlayer.getIdPlayer(), "errore", 0);
                    nextTurn();
                }
            }else{
                if(moveDone)
                    manche.getTurns().getLastMove().setOutCome(0);
                else
                    manche.getTurns().addMove(activePlayer.getIdPlayer(), "errore", 0);
                nextTurn();
            }
        }catch(RemoteException e){
            ServerImplementation.serverError(null);
        }
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
        spinnedWheel = false;
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
                    p.getClient().notifyNewTurn(p.getNickname());
                    p.getClient().notifyYourTurn();
                    startTimer(5000, false, false, false);
                } else {
                    p.getClient().notifyNewTurn(activePlayer.getNickname());
                }
            }catch(RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    public void askForSolution() throws RemoteException{
        if(firstTurn || timer.isThisForJolly()){
            timer.interrupt();
            errorInTurn(true,false);
            return;
        }
        notifySolutionAsk();
        startTimer(10000, false, true, false);
    }

    private void notifySolutionAsk() throws RemoteException{
        Player activePlayer = players.get(turn);
        for(Client c : observers){
            try {
                c.notifyTryForSolution(activePlayer.getNickname());
            }catch(RemoteException e){
                leaveMatchAsObserver(c);
            }
        }
        for(Player p : players){
            try {
                if (p.equals(activePlayer)) {
                } else {
                    p.getClient().notifyTryForSolution(activePlayer.getNickname());
                }
            }catch(RemoteException e){
                leaveMatchAsPlayer(p);
            }
        }
    }

    public void giveSolution(String solution) throws RemoteException {
        Player activePlayer = players.get(turn);
        if(firstTurn || timer.isThisForJolly() || !timer.isThisForSolution() || timer.isThisForVocal()){
            timer.interrupt();
            errorInTurn(true,false);
            return;
        }
        timer.interrupt();
        if(solution.equals(manche.getCurrentPhrase())){
            manche.getTurns().addMove(activePlayer.getIdPlayer(), "soluzione", -1);
            endManche(activePlayer);
        }else{
            manche.getTurns().addMove(activePlayer.getIdPlayer(), "soluzione", 0);
            errorInTurn(true,true);
        }
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

            if(phrases == null || phrases.isEmpty()){
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
        }catch(RemoteException e) {
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
        StringTokenizer st = new StringTokenizer(phrase," ',!?.:;\"/()\\^<>-+*");
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
                       p.updatePoints(p.getPartialPoints());
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
                       Player p = null;
                       for(int i=0; i<3; i++) {
                           p = players.get(i);
                           c.notifyPlayerStats(i, p.getNickname(), 0, p.getPoints(), p.getNumJolly());
                       }
                       dbManager.addMancheJoiner(id, manche.getNumManche(), c.getId(), true);
                   }catch(RemoteException e){
                       leaveMatchAsObserver(c);
                   }
               }
               for (Player p : players) {
                   try {
                       p.getClient().notifyNewManche(manche.getNumManche());
                       p.getClient().setNewPhrase(newPhrase.getTheme(), newPhrase.getPhrase());
                       Player pl = null;
                       for(int i=0; i<3; i++) {
                           pl = players.get(i);
                           p.getClient().notifyPlayerStats(i, pl.getNickname(), 0, pl.getPoints(), pl.getNumJolly());
                       }
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
        int num = 0;
        for(Player p : players){
            if(p.getClient().equals(c))
                break;
            else
                num++;
        }
            for (Player p : players) {
                try {
                    if (p.getClient().equals(c)) {
                        players.remove(p);
                    } else {
                        p.getClient().notifyLeaver(name);
                        p.getClient().notifyPlayerStats(num, "--", 0,0,0);
                    }
                } catch (RemoteException e) {
                    players.remove(p);
                }
            }
            for (Client client : observers) {
                try {
                    client.notifyLeaver(name);
                    c.notifyPlayerStats(num, "--", 0,0,0);
                } catch (RemoteException e) {
                    leaveMatchAsObserver(client);
                }
            }
        endManche(null);
        endMatch(false);
    }

    void leaveMatchAsPlayer(Player player) throws RemoteException {
        String name = player.getNickname();
        int num = 0;
        for(Player p : players){
            if(p.equals(player))
                break;
            else
                num++;
        }
        for (Player p : players) {
            try {
                if (p.getClient().equals(player)) {
                    players.remove(p);
                } else {
                    p.getClient().notifyLeaver(name);
                    p.getClient().notifyPlayerStats(num, "--", 0,0,0);
                }
            }catch(RemoteException e){
                players.remove(p);
            }
        }
        for (Client client : observers) {
            try {
                client.notifyLeaver(name);
                client.notifyPlayerStats(num, "--", 0,0,0);
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
            Player p = null;
            for(int i=0; i<players.size(); i++){
                p = players.get(i);
                c.notifyPlayerStats(i, p.getNickname(), p.getPartialPoints(), p.getPoints(), p.getNumJolly());
                c.setNewPhrase(manche.getCurrentPhrase().getTheme(), manche.getCurrentPhrase().getPhrase());
                c.updatePhrase(phraseStatus);
            }
        }else{
            for(int i=0; i<players.size(); i++) {
                c.notifyPlayerStats(i + 1, players.get(i).getNickname(), 0, 0, 0);
            }
        }
    }

    @Override
    public String getMatchId() throws RemoteException {
        return id;
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

    List<Player> getPlayers() {
        return players;
    }

    List<Client> getObservers() {
        return observers;
    }

    Manche getManche() {
        return manche;
    }

    public int getTurn() {
        return turn;
    }

    private void startTimer(int time, boolean jolly, boolean solution, boolean vocal){
        timer = new MoveTimer(time, this, jolly, solution, vocal);
        timer.start();
    }
}