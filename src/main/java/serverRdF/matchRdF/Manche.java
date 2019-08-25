package serverRdF.matchRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.ManchesDTO;
import serverRdF.dbComm.MatchesDTO;
import serverRdF.dbComm.PhrasesDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce le manche e i turni.
 */
public class Manche {

    private Turn turns;
    /*
    La frase "attiva" è quella in posizione numManche-1 (dato che le manche sono numerate da 1 a 5)
     */
    private List<PhrasesDTO> phrases;
    private int numManche;
    private DBManager dbManager;
    private String match;
    private LocalDateTime matchTime;

    public Manche(DBManager dbmng, String id, LocalDateTime time) {
        turns = new Turn(this);
        phrases = new ArrayList<PhrasesDTO>();
        numManche = 0;
        dbManager = dbmng;
        this.match = id;
        matchTime = time;
    }

    public Turn getTurns() {
        return turns;
    }

    public void setTurns(Turn turns) {
        this.turns = turns;
    }

    public List<PhrasesDTO> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<PhrasesDTO> phrases) {
        this.phrases = phrases;
    }

    public int getNumManche() {
        return numManche;
    }

    public void setNumManche(int numManche) {
        this.numManche = numManche;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public PhrasesDTO getCurrentPhrase() {
        return phrases.get(numManche - 1);
    }


    /**
     * Questo metodo aggiorna l'oggetto per permettere l'inizo della manche successiva. La lista di mosse viene svuotata e le mosse vengono salvate nel database
     *
     * @param winner l'eventuale vincitore della manche
     * @return <code>true</code> se il metodo viene eseguito con successo, <code>false</code> altrimenti
     */
    public boolean endManche(Player winner) {
        ManchesDTO manche = new ManchesDTO();
        manche.setNumber(numManche);
        manche.setPhrase(getCurrentPhrase());
        manche.setMatch(new MatchesDTO(match, matchTime));
        boolean man = dbManager.addManche(manche);
        boolean tur = turns.saveMoves(dbManager);
        if (winner != null) {
            setNumManche(numManche + 1);
            return dbManager.addMancheWinner(winner.getIdPlayer(), manche, winner.getPartialPoints());
        }
        if (man && tur) {
            return true;
        } else
            return false;
    }
}
