package serverRdF.matchRdF;

import rdFUtil.client.Client;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.DBManagerImplementation;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class MatchManager {
    private HashMap<String, Match> matches;
    private static MatchManager matchManager = null;
    private DBManager dbManager;

    private MatchManager(DBManager){
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

    public RemoteMatch createMatch(Client c){
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            Match match = new Match(id);
            dbManagerg
        }catch(RemoteException e){
            try {
                c.notifyServerError();
            }catch(RemoteException ex){
                System.err.println(ex.getMessage());
            }
        }

    }

    public RemoteMatch joinMatch(Client c, String idMatch){

    }

    public RemoteMatch observeMatch(Client c, String idMatch){

    }

    //TODO
}
