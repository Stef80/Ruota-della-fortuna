package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBManager implements DBManagerInterface{
    //TODO bisogna implementare i metodi dell'interfaccia
    private static DBManager dbManager=null;
    private Connection con;
    private PhrasesDAO phrasesDAO;
    private MatchesDAO matchesDAO;

    private DBManager() throws SQLException {
        //TODO connessione con database
        con = DriverManager.getConnection("DB","userID", "password");
    }

    /**
     * @return dbManager il singleton di tipo DBManager
     * @throws SQLException
     */
    public static DBManager createDBManager() throws SQLException {
        if (dbManager == null) {
            dbManager = new DBManager();
            return dbManager;
        } else
            return dbManager;
    }

    /**
     * Questo metodo inizializza l'instanzia di MatchedDAO
     */
    private void createMatchesDAO(){
        matchesDAO = new MatchesDAOImpl(con);
    }

    public boolean addMatch(String id, LocalDateTime time) throws SQLException {
        if(matchesDAO==null)
            createMatchesDAO();
        return matchesDAO.addMatch(new MatchesDTO(id, time));
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

    /**
     * Questo metodo inizializza l'instanzia di PhrasesDAO
     */
    private void createPhrasesDAO(){
        phrasesDAO = new PhrasesDAOImpl(con);
    }

    @Override
    public List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) throws SQLException{
        if(phrasesDAO ==null)
            createPhrasesDAO();
       return phrasesDAO.get5Phrases(idPlayer1,idPlayer2,idPlayer3);
    }

    @Override
    public boolean addPhrases(ArrayList<String> phrases, ArrayList<String> themes) throws SQLException{
        if(phrasesDAO ==null)
            createPhrasesDAO();
        ArrayList<PhrasesDTO> pDTO = new ArrayList<>();
        for(int i=0; i<phrases.size();i++){
            pDTO.add(new PhrasesDTO(themes.get(i),phrases.get(i)));
        }
        return phrasesDAO.addPhrases(pDTO);
    }

    @Override
    public List<PhrasesDTO> getAllPhrases() throws SQLException{
        if(phrasesDAO ==null)
            createPhrasesDAO();
       return phrasesDAO.getAllPhrases();
    }
}
