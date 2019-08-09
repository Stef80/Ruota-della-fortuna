package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.SQLException;
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


    public boolean addMatch(String id, LocalDateTime time) {
        //TODO
        return false;
    }

    public boolean addUser(User user){
        //TODO
        return false;
    }

    @Override
    public UsersDTO getUser(boolean email, String unique) {
        return null;
    }

    @Override
    public boolean checkLogin(String email, String password) throws SQLException {
        return false;
    }
}
