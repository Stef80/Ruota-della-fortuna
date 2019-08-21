package serverRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.MovesDTO;

public class MonitoringManager {
    private DBManager dbManager;
    private static MonitoringManager monitoringManager;
    private MonitoringManager(DBManager dbmng){
        dbManager = dbmng;
    }

    /**
     *
     * @param dbmng il riferimento al gestore del db
     * @return il singleton della classe {@link MonitoringManager}
     */
    public static MonitoringManager createMonitoringManager(DBManager dbmng){
        if(monitoringManager == null){
            monitoringManager = new MonitoringManager(dbmng);
            return monitoringManager;
        }else{
            return monitoringManager;
        }
    }

    public String getBestMove(){
        MovesDTO bestMove = dbManager.getBestMove();
        String result = ""+ bestMove.getPlayer().getNickname() +" " +bestMove.getMoveType() +" "+ bestMove.getManche().getPhrase().getPhrase();
        return result;
    }

    public int averageMovesPerManches(){
        return dbManager.get
    }
    //TODO metodi
}
