package serverRdF.matchRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.ManchesDTO;
import serverRdF.dbComm.MatchesDTO;
import serverRdF.dbComm.PhrasesDTO;
import serverRdF.matchRdF.Turn;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Manche(DBManager dbmng, String id, LocalDateTime time){
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

    public PhrasesDTO getCurrentPhrase(){
        return phrases.get(numManche-1);
    }


    public boolean endManche(Player winner){
        ManchesDTO manche = new ManchesDTO();
        manche.setNumber(numManche);
        manche.setPhrase(getCurrentPhrase());
        manche.setMatch(new MatchesDTO(match, matchTime));
        try {
            dbManager.addManche(manche);
            if(winner != null){
                dbManager.addMancheWinner(winner.getIdPlayer(), manche, winner.getPartialPoints());
                setNumManche(numManche+1);
            }
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    //TODO metodi per gestione (esempio quando inizia nuova manche o finisce una manche con conseguente salvataggio nel DB)
}
