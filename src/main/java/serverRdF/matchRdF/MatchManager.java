package serverRdF.matchRdF;

import rdFUtil.client.Client;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class MatchManager {
    private HashMap<String, Match> matches;
    private static MatchManager matchManager = null;

    private MatchManager(){
        matches = new HashMap<String,Match>();
    }


    /**
     * @return matchManager il singleton di tipo MatchManager
     */
    public static MatchManager createMatchManager(){
        if(matchManager == null) {
            matchManager = new MatchManager();
            return matchManager;
        }else
            return matchManager;
    }

    public void createMatch(Client c){
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.now();

    }

    public 

    //TODO
}
