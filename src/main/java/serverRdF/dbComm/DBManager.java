package serverRdF.dbComm;

import java.time.LocalDateTime;

public class DBManager implements DBManagerInterface{
    //TODO bisogna implementare i metodi dell'interfaccia
    private static DBManager dbManager=null;
    private DBManager(){}

    /**
     * @return dbManager il singleton di tipo DBManager
     */
    public static DBManager createDBManager(){
        if (dbManager == null) {
            dbManager = new DBManager();
            return dbManager;
        } else
            return dbManager;
    }
    @Override
    public boolean addMatch(String id, LocalDateTime time) {
        //TODO
        return false;
    }
}
