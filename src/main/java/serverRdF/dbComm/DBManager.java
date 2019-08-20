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
    private ManchesDAO manchesDAO;
    private MancheWinnersDAO mancheWinnersDAO;
    private MancheJoinersDAO mancheJoinersDAO;
    private MatchWinnersDAO matchWinnersDAO;

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
    public int checkLogin(String email, String password, boolean admin) throws SQLException {
        UsersDTO user = getUserByEmail(email);
        if(user == null){
            return -1;
        }else{
            if(user.getPassword().equals(password)){
                if(user.isAdmin() == admin){
                    return 0;
                }else{
                    return 1;
                }
            }else{
                return -1;
            }
        }
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
    public boolean addPhrases(ArrayList<PhrasesDTO> phrases) throws SQLException{
        if(phrasesDAO ==null)
            createPhrasesDAO();
        return phrasesDAO.addPhrases(phrases);
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

    /**
     * Questo metodo inizializza l'istanza di ManchesDAO
     */
    private void createManchesDAO(){
        manchesDAO = new ManchesDAOImpl(con);
    }

    @Override
    public boolean addManche(ManchesDTO manche) throws SQLException{
        if(manchesDAO==null)
            createManchesDAO();
        return manchesDAO.addManche(manche);
    }

    /**
     * Questo metodo inizializza l'istanza di MancheWinnersDAO
     */
    private void createMancheWinnersDAO(){mancheWinnersDAO = new MancheWinnersDAOImpl(con);}

    @Override
    public boolean addMancheWinner(String idPlayer, ManchesDTO manche, int amount) throws SQLException{
        if(mancheWinnersDAO==null)
            createMancheWinnersDAO();
        return mancheWinnersDAO.addMancheWinner(idPlayer,manche,amount);
    }

    private void createMancheJoinersDAO(){mancheJoinersDAO = new MancheJoinersDAOImpl(con);}

    @Override
    public boolean addMancheJoiner(String idMatch, int numManche, String userId, boolean observer) {
        if(mancheJoinersDAO==null)
            createMancheWinnersDAO();
        try {
            return mancheJoinersDAO.addMancheJoiner(idMatch, numManche, userId, observer);
        } catch (SQLException e) {
            return false;
        }
    }

    private void createMatchWinnersDAO(){matchWinnersDAO = new MatchWinnersDAOImpl(con);}

    @Override
    public boolean addMatchWinner(String idMatch, String idPlayer, int amount) {
        if(matchWinnersDAO==null)
            createMatchWinnersDAO();
        try {
            return matchWinnersDAO.addMatchWinner(idMatch, idPlayer, amount);
        } catch (SQLException e) {
            return false;
        }
    }
}
