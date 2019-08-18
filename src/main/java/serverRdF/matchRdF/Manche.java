package serverRdF.matchRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.PhrasesDTO;
import serverRdF.matchRdF.Turn;

import java.util.ArrayList;
import java.util.List;

public class Manche {

    private List<Turn> turns;
    private List<PhrasesDTO> phrases;
    private int numManche;
    private DBManager dbManager;

    public Manche(DBManager dbmng){
        turns = new ArrayList<Turn>();
        phrases = new ArrayList<PhrasesDTO>();
        numManche = 0;
        dbManager = dbmng;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
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

    //TODO metodi per gestione (esempio quando inizia nuova manche o finisce una manche con conseguente salvataggio nel DB)
}
