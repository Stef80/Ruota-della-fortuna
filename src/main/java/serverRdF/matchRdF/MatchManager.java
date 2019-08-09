package serverRdF.matchRdF;

import rdFUtil.client.Client;
import serverRdF.ServerImplementation;
import serverRdF.dbComm.DBManager;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;


/**
 * Questa e' la classe che si occupa della gestione delle partita in attesa di giocatori e in corso.
 * <p>
 * Permette la creazione e la partecipazione alle partite, le quali sono contenute in una tabella hash accessibile tramite l'apposito getter.
 */
public class MatchManager {
    private HashMap<String, Match> matches;
    private static MatchManager matchManager = null;
    private DBManager dbManager;

    private MatchManager(DBManager dbmng) {
        dbManager = dbmng;
        matches = new HashMap<String, Match>();
    }


    /**
     * @param dbmng //TODO
     * @return matchManager il singleton di tipo MatchManager
     */
    public static MatchManager createMatchManager(DBManager dbmng) {
        if (matchManager == null) {
            matchManager = new MatchManager(dbmng);
            return matchManager;
        } else
            return matchManager;
    }


    /**
     * Il client richiamera' questo metodo per creare una partita e ricevera' il refiremento all'oggetto che si occupera' della gestione del singolo Match
     *
     * @param c il riferimento del Client che sara' fornito all'oggetto remoto {@link Match} in modo da poter inviargli le notifiche (Observers design pattern)
     * @return match un riferimento all'oggetto remoto {@link RemoteMatch} della partita appena creata.
     */
    public RemoteMatch createMatch(Client c) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.now();
        Match match = null;
        try {
            boolean bool = dbManager.addMatch(id, currentTime);
            if (!bool) {
                try {
                    c.notifyServerError();
                    throw new RuntimeException();
                } catch (RemoteException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            match = new Match(id, currentTime);
            match.addPlayer(c);
            matches.put(id, match);
            return match;
        } catch (RemoteException e) {
            ServerImplementation.serverError(c);
        } finally {
            return match;
        }
    }

    /**
     * @param c       il riferimento del Client che sara' fornito all'oggetto remoto {@link Match} in modo da poter inviargli le notifiche (Observers design pattern)
     * @param idMatch il nome del match al quale si vuole partecipare
     * @return un riferimento all'oggetto remoto {@link RemoteMatch} della partita a cui si ha appena partecipato, o null nel caso in cui la partita sia piena o ci siano stati problemi con la connessione al server
     */
    public synchronized RemoteMatch joinMatch(Client c, String idMatch) {
        Match match = matches.get(idMatch);
        boolean full;
        try {
            full = match.addPlayer(c);
            if (full) {
                try {
                    c.notifyTooManyPlayers();
                    return null;
                } catch (RemoteException e) {
                    try {
                        c.notifyServerError();
                        return null;
                    } catch (RemoteException ex) {
                        System.err.println(ex.getMessage());
                        return null;
                    }
                }
            }
        } catch (RemoteException e) {
            try {
                c.notifyServerError();
                return null;
            } catch (RemoteException ex) {
                System.err.println(ex.getMessage());
                return null;
            }
        }
        return match;
    }


    /**
     * @param c       il riferimento del Client che sara' fornito all'oggetto remoto {@link Match} in modo da poter inviargli le notifiche (Observers design pattern)
     * @param idMatch il nome del match al quale si vuole partecipare
     * @return match un riferimento all'oggetto remoto {@link RemoteMatch} della partita a cui si ha appena partecipato come osservatore, o null nel caso in cui ci siano stati problemi con la connessione al server
     */
    public RemoteMatch observeMatch(Client c, String idMatch) {
        Match match = matches.get(idMatch);
        try {
            match.addObserver(c);
        } catch (RemoteException e) {
            try {
                c.notifyServerError();
                return null;
            } catch (RemoteException ex) {
                System.err.println(ex.getMessage());
                return null;
            }
        }
        return match;
    }

    public HashMap<String, Match> getMatches() {
        return matches;
    }
}
