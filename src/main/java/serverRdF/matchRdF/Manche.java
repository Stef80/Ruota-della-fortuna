package serverRdF.matchRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.ManchesDTO;
import serverRdF.dbComm.PhrasesDTO;
import serverRdF.matchRdF.Turn;

import java.util.ArrayList;
import java.util.List;

public class Manche {

    private Turn turns;
    private List<PhrasesDTO> phrases;
    private int numManche;
    private DBManager dbManager;
    private String match;

    public Manche(DBManager dbmng, String id){
        turns = new Turn(this);
        phrases = new ArrayList<PhrasesDTO>();
        numManche = 0;
        dbManager = dbmng;
        this.match = id;
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


    public void endManche(Player winner){
        ManchesDTO manche = new ManchesDTO();
        manche.setNumber(numManche);
        //TODO
        if()
    }
    //TODO metodi per gestione (esempio quando inizia nuova manche o finisce una manche con conseguente salvataggio nel DB)
}
