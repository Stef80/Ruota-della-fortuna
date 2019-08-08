package serverRdF;

import rdFUtil.MatchData;
import serverRdF.matchRdF.MatchManager;
import serverRdF.matchRdF.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * questa è la classe che si occupa di comunicare al client i match in attesa di giocatori e in corso, ai quali si potrà partecipare come giocatore o osservatore
 */
public class MatchVisualizer {
    private static MatchVisualizer matchVisualizer = null;
    private MatchManager matchManager;

    private MatchVisualizer(MatchManager matchmng){

    }

    /**
     *
     * @return matchVisualizer il singleton di tipo MatchVisualizer
     */
    public static MatchVisualizer createMatchVisualizer(MatchManager matchmng){
        if (matchVisualizer == null) {
            matchVisualizer = new MatchVisualizer(matchmng);
            return matchVisualizer;
        } else
            return matchVisualizer;
    }


    /**
     *
     * @return list una lista contenente i dati delle partite da visulizzare
     */
    public ArrayList<MatchData> visualizeMatch(){
        HashMap<String, Match> hash = matchManager.getMatches();
        Collection<Match> collection = hash.values();
        ArrayList<MatchData> list = new ArrayList<>();
        for(Match match : collection){
            list.add(match.createMatchData());
        }
        return list;
    }
}
