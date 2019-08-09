package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBManager implements DBManagerInterface{
    //TODO bisogna implementare i metodi dell'interfaccia
    private static DBManager dbManager=null;
    PhrasesDAO pDAO = new PhrasesDAOImpl();
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
    public UsersDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public UsersDTO getUserByNickname(String nickname) {
        return null;
    }

    @Override
    public UsersDTO getUserById(String id) {
        return null;
    }

    @Override
    public boolean checkLogin(String email, String password) throws SQLException {
        //TODO
        return false;
    }

    @Override
    public List<PhrasesDTO> get5Phrases(int idPlayer1, int idPlayer2, int idPlayer3) {
        return pDAO.get5Phrases(idPlayer1,idPlayer2,idPlayer3);
    }

    @Override
    public boolean addPhrase(ArrayList<PhrasesDTO> phrases) {
        return pDAO.addPhrase(phrases);
    }

    @Override
    public List<PhrasesDTO> getAllPhrases() {
       return pDAO.getAllPhrases();
    }
}
