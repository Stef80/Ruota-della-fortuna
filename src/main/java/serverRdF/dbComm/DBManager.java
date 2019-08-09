package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBManager implements DBManagerInterface{
    //TODO bisogna implementare i metodi dell'interfaccia
    private static DBManager dbManager=null;
    private PhrasesDAO pDAO = new PhrasesDAOImpl();

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
        //TODO
        return null;
    }

    @Override
    public boolean checkLogin(String email, String password) throws SQLException {
        //TODO
        return false;
    }

    @Override
    public List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) {
       return pDAO.get5Phrases(idPlayer1,idPlayer2,idPlayer3);
    }

    @Override
    public boolean addPhrase(ArrayList<String> phrases, ArrayList<String> themes){
        ArrayList<PhrasesDTO> pDTO = new ArrayList<>();
        for(int i=0; i<phrases.size();i++){
            pDTO.add(new PhrasesDTO(themes.get(i),phrases.get(i)));
        }
        return pDAO.addPhrase(pDTO);
    }

    @Override
    public List<PhrasesDTO> getAllPhrases() {
       return pDAO.getAllPhrases();
    }
}
