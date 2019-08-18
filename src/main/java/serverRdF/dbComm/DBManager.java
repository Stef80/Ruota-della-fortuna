package serverRdF.dbComm;

import rdFUtil.logging.User;
import serverRdF.matchRdF.Move;

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
    private UsersDAO usersDAO;
    private MovesDAO movesDAO;

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
     * Questo metodo inizializza l'instanza di MatchedDAO
     */
    private void createMatchesDAO(){
        matchesDAO = new MatchesDAOImpl(con);
    }

    public boolean addMatch(String id, LocalDateTime time) throws SQLException {
        if(matchesDAO==null)
            createMatchesDAO();
        return matchesDAO.addMatch(new MatchesDTO(id, time));
    }

    public boolean deleteMatch(String idMatch) throws SQLException{
        return matchesDAO.deleteMatch(idMatch);
    }

    /**
     * Questo metodo inizializza l'instanza di UsersDAO
     */
    private void createUsersDAO(){
        usersDAO = new UsersDAOImpl(con);
    }
    public boolean addUser(User user, boolean isAdmin) throws SQLException {
        if(usersDAO==null)
            createUsersDAO();
        return usersDAO.addUser(new UsersDTO(user.getId(), isAdmin, user.getName(), user.getSurname(), user.getNickname(), user.getEmail(), user.getPasswordC()));
    }

    @Override
    public UsersDTO getUserByEmail(String email) throws SQLException {
        if(usersDAO==null)
            createUsersDAO();
        return usersDAO.getUserByEmail(email);
    }

    @Override
    public UsersDTO getUserByNickname(String nickname) throws SQLException {
        if(usersDAO==null)
            createUsersDAO();
        return usersDAO.getUserByNickname(nickname);
    }

    @Override
    public UsersDTO getUserById(String id) throws SQLException {
        if(usersDAO==null)
            createUsersDAO();
        return usersDAO.getUserById(id);
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        if(usersDAO==null)
            createUsersDAO();
        return usersDAO.deleteUser(id);
    }

    @Override
    public boolean checkLogin(String email, String password) throws SQLException {
        //TODO
        return false;
    }

    public List<UsersDTO> getAllAdmin() throws SQLException{
        //TODO
        return null;
    }

    /**
     * Questo metodo inizializza l'instanza di PhrasesDAO
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

    /**
     * Questo metodo inizializza l'instanza di MovesDAO
     */
    private void createMovesDAO(){
        movesDAO = new MovesDAOImpl(con);
    }

    @Override
    public boolean addMove(Move move) throws SQLException{
        if(movesDAO==null)
            createMovesDAO();
        return movesDAO.addMove(move);
    }
}
