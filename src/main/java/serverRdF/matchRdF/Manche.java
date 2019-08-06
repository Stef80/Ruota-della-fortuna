package serverRdF.matchRdF;

import serverRdF.dbComm.PhrasesDTO;
import serverRdF.matchRdF.Turn;

import java.util.ArrayList;
import java.util.List;

public class Manche {

    private List<Turn> turns;
    private List<PhrasesDTO> phrases;
    private int numManche;

    public Manche(){
        turns = new ArrayList<Turn>();
        phrases = new ArrayList<PhrasesDTO>();
        numManche = 0;
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

    //TODO metodo per iniziare nuova manche (con salvattaggi nel database)
}
