package server.dbComm;

import util.logging.User;
import server.match.Move;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link DBManagerInterface}
 */
public class DBManager implements DBManagerInterface{
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

    private DBManager(String url, String userID, String password) throws SQLException {
        con = DriverManager.getConnection("jdbc:postgresql://" + url +"/dbrdf",userID, password);
    }

    /**
     * @param url l'url del database
     * @param userID l'utente del database
     * @param password password dell'utente del database
     * @return dbManager il singleton di tipo DBManager
     * @throws SQLException
     */
    public static DBManager createDBManager(String url, String userID, String password) throws SQLException {//TODO url del database
        if (dbManager == null) {
            dbManager = new DBManager(url,userID, password);
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

    public synchronized boolean addMatch(String id, LocalDateTime time) {
        if(matchesDAO==null)
            createMatchesDAO();
        try {
            return matchesDAO.addMatch(new MatchesDTO(id, time));
        }catch (SQLException e){
            System.err.println("SQLException in addMatch");
            return false;
        }
    }

    public synchronized boolean deleteMatch(String idMatch) {
        if(matchesDAO == null)
            createMatchesDAO();
        try {
            return matchesDAO.deleteMatch(idMatch);
        }catch (SQLException e){
            System.err.println("SQLException in deleteMatch");
            return false;
        }
    }

    /**
     * Questo metodo inizializza l'instanza di UsersDAO
     */
    private synchronized void createUsersDAO(){
        usersDAO = new UsersDAOImpl(con);
    }
    public boolean addUser(User user, boolean isAdmin) {
        if(usersDAO==null)
            createUsersDAO();
        try {
            return usersDAO.addUser(new UsersDTO(user.getId(), isAdmin, user.getName(), user.getSurname(), user.getNickname(), user.getEmail(), user.getPasswordC()));
        }catch (SQLException e){System.err.println("SQLException in addUser");
            return false;
        }
    }

    @Override
    public UsersDTO getUserByEmail(String email) {
        if(usersDAO==null)
            createUsersDAO();
        try {
            return usersDAO.getUserByEmail(email);
        }catch (SQLException e){
            System.err.println("SQLException in getUserByEmail");
            return null;
        }
    }

    @Override
    public UsersDTO getUserByNickname(String nickname) {
        if(usersDAO==null)
            createUsersDAO();
        try {
            return usersDAO.getUserByNickname(nickname);
        }catch (SQLException e){
            System.out.println("SQLException in getUserByNickname");
            return null;
        }
    }

    @Override
    public UsersDTO getUserById(String id) {
        if(usersDAO==null)
            createUsersDAO();
        try {
            return usersDAO.getUserById(id);
        }catch (SQLException e){System.err.println("SQLException in getUserById");
            return null;
        }
    }


    @Override
    public synchronized boolean updateUser(UsersDTO user) {
        if(usersDAO==null)
            createUsersDAO();
        try {
            return usersDAO.updateUser(user);
        }catch (SQLException e){System.err.println("SQLException in updateUser");
            return false;
        }
    }

    @Override
    public synchronized boolean deleteUser(String id) {
        if(usersDAO==null)
            createUsersDAO();
        try {
            return usersDAO.deleteUser(id);
        }catch (SQLException e){System.err.println("SQLException in deleteUser");
            return false;
        }
    }

    @Override
    public int checkLogin(String email, String password, boolean admin) {
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

    @Override
    public boolean getAnyAdmin() {
        if(usersDAO==null){
            createUsersDAO();
        }
        try {
            List<UsersDTO> list = usersDAO.getAllAdmin();
            if(list == null || list.isEmpty()){
                return false;
            }else
                return true;
        }catch (SQLException e){System.err.println("SQLException in getAnyAdmin");
            return false;
        }
    }

    @Override
    public List<UsersDTO> getAllAdmin() {
        if(usersDAO==null){
            createUsersDAO();
        }
        try {
            return usersDAO.getAllAdmin();
        }catch (SQLException e){System.err.println("SQLException in getAllAdmin");
            return null;
        }
    }

    @Override
    public UsersDTO getBestUserForManche() {
        if(usersDAO==null)
            createUsersDAO();
        try{
            return usersDAO.getBestPlayerByManche();
        }catch (SQLException e){System.err.println("SQLException in getBestUserForManche");
            return null;
        }
    }

    @Override
    public UsersDTO getBestUserForMatch() {
        if(usersDAO == null)
            createUsersDAO();
        try{
            return usersDAO.getBestPlayerByMatch();
        }catch (SQLException e){System.err.println("SQLException in getBestUserForMatch");
            return null;
        }
    }

    @Override
    public UsersDTO getUserForMoreManchesPlayed() {
        if(usersDAO == null)
            createUsersDAO();
        try{
            return usersDAO.getUserForMoreManchesPlayed();
        }catch (SQLException e){
            System.out.println("SQLException in getUserForMostManchePlayed");
            return null;
        }
    }

    @Override
    public UsersDTO getUserForBestMancheAverage() {
        if(usersDAO == null)
            createUsersDAO();
        try{
            return usersDAO.getUserForBestMancheAverage();
        }catch (SQLException e){System.err.println("SQLException in getUserForBestMancheAverage");
            return null;
        }
    }

    @Override
    public UsersDTO getUserForMostLostTurn() {
        if(usersDAO == null)
            createUsersDAO();
        try{
            return usersDAO.getUserForMostLostTurn();
        }catch (SQLException e){System.err.println("SQLException in getUserForMostLostTurn");
            return null;
        }
    }

    @Override
    public UsersDTO getUserForMostLosses() {
        if(usersDAO == null)
            createUsersDAO();
        try{
            return usersDAO.getUserForMostLosses();
        }catch (SQLException e){System.err.println("SQLException in getUserForMostLosses");
            return null;
        }
    }

    /**
     * Questo metodo inizializza l'instanza di PhrasesDAO
     */
    private void createPhrasesDAO(){
        phrasesDAO = new PhrasesDAOImpl(con);
    }

    @Override
    public List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) {
        if(phrasesDAO ==null)
            createPhrasesDAO();
        try {
            return phrasesDAO.get5Phrases(idPlayer1, idPlayer2, idPlayer3);
        }catch (SQLException e){System.err.println("SQLException in get5Phrases");
            return null;
        }
    }

    @Override
    public synchronized boolean addPhrases(ArrayList<PhrasesDTO> phrases) {
        if(phrasesDAO ==null)
            createPhrasesDAO();
        try {
            return phrasesDAO.addPhrases(phrases);
        }catch (SQLException e){System.err.println("SQLException in addPhrases");
            return false;
        }
    }

    @Override
    public List<PhrasesDTO> getAllPhrases() {
        if(phrasesDAO ==null)
            createPhrasesDAO();
        try {
            return phrasesDAO.getAllPhrases();
        }catch (SQLException e){System.err.println("SQLException in getAllPhrases");
            return null;
        }
    }

    /**
     * Questo metodo inizializza l'instanza di MovesDAO
     */
    private void createMovesDAO(){
        movesDAO = new MovesDAOImpl(con);
    }

    @Override
    public synchronized boolean addMove(Move move) {
        if(movesDAO==null)
            createMovesDAO();
        try {
            return movesDAO.addMove(move);
        }catch (SQLException e){System.err.println("SQLException in addMove");
            return false;
        }
    }

    @Override
    public MovesDTO getBestMove() {
        if(movesDAO==null)
            createMovesDAO();
        try {
            return movesDAO.getBestMove();
        }catch (SQLException e){System.err.println("SQLException in getBestMove");
            return null;
        }
    }

    @Override
    public int getAverageMovesPerManche() {
        if(movesDAO == null)
            createMovesDAO();
        if(mancheWinnersDAO == null)
            createMancheWinnersDAO();
        try{
            int n = mancheWinnersDAO.getNumWinnedManches();
            return movesDAO.getAverageMovesPerManche(n);
        }catch (SQLException e){System.err.println("SQLException in getAverageMovesPerManche");
            return -1;
        }
    }

    @Override
    public int getAveragePassedTurnPerMancheByUser(String id) {
        if(movesDAO == null)
            createMovesDAO();
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            int totalMoves = movesDAO.getAllPassedTurnByUser(id);
            int totManches = mancheJoinersDAO.getManchePlayedByUser(id);
            if(totManches == 0)
                return 0;
            return totalMoves/totManches;
        }catch (SQLException e){System.err.println("SQLException in getAveragePassedTurnPerMancheByUser");
            return 0;
        }
    }

    @Override
    public int getAveragePassedTurnPerMatchByUser(String id) {
        if(movesDAO == null)
            createMovesDAO();
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            int totalMoves = movesDAO.getAllPassedTurnByUser(id);
            int totMatches = mancheJoinersDAO.getMatchesPlayedByUser(id);
            if(totMatches == 0)
                return 0;
            return totalMoves/totMatches;
        }catch (SQLException e){System.err.println("SQLException in getAveragePassedTurnPerMatchByUser");
            return 0;
        }
    }

    @Override
    public int getAverageLossPerMancheByUser(String id) {
        if(movesDAO == null)
            createMovesDAO();
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            int totalMoves = movesDAO.getAllLossesByUser(id);
            int totManches = mancheJoinersDAO.getManchePlayedByUser(id);
            if(totManches == 0)
                return 0;
            return totalMoves/totManches;
        }catch (SQLException e){System.err.println("SQLException in getAverageLossPerMancheByUser");
            return 0;
        }
    }

    @Override
    public int getAverageLossPerMatchByUser(String id) {
        if(movesDAO == null)
            createMovesDAO();
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            int totalMoves = movesDAO.getAllLossesByUser(id);
            int totMatches = mancheJoinersDAO.getMatchesPlayedByUser(id);
            if(totMatches == 0)
                return 0;
            return totalMoves/totMatches;
        }catch (SQLException e){System.err.println("SQLException in getAverageLossPerMatchByUser");
            return 0;
        }
    }

    /**
     * Questo metodo inizializza l'istanza di ManchesDAO
     */
    private void createManchesDAO(){
        manchesDAO = new ManchesDAOImpl(con);
    }

    @Override
    public synchronized boolean addManche(ManchesDTO manche) {
        if(manchesDAO==null)
            createManchesDAO();
        try {
            return manchesDAO.addManche(manche);
        }catch (SQLException e){System.err.println("SQLException in addManche");
            return false;
        }
    }

    /**
     * Questo metodo inizializza l'istanza di MancheWinnersDAO
     */
    private void createMancheWinnersDAO(){mancheWinnersDAO = new MancheWinnersDAOImpl(con);}

    @Override
    public synchronized boolean addMancheWinner(String idPlayer, ManchesDTO manche, int amount) {
        if(mancheWinnersDAO==null)
            createMancheWinnersDAO();
        try {
            return mancheWinnersDAO.addMancheWinner(idPlayer, manche, amount);
        }catch (SQLException e){System.err.println("SQLException in addMancheWinner");
            return false;
        }
    }

    @Override
    public int getWonManchesByUser(String id) {
        if(mancheWinnersDAO==null)
            createMancheWinnersDAO();
        try{
            return mancheWinnersDAO.getMancheWonByUser(id);
        }catch (SQLException e){System.err.println("SQLException in getWonManchesByUser");
            return 0;
        }
    }

    private void createMancheJoinersDAO(){mancheJoinersDAO = new MancheJoinersDAOImpl(con);}

    @Override
    public synchronized boolean addMancheJoiner(String idMatch, int numManche, String userId, boolean observer) {
        if(mancheJoinersDAO==null)
            createMancheJoinersDAO();
        try {
            return mancheJoinersDAO.addMancheJoiner(idMatch, numManche, userId, observer);
        } catch (SQLException e) {
            System.err.println("SQLException in addMancheJoiner");
            return false;
        }
    }

    @Override
    public int getManchePlayedByUser(String id) {
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            return mancheJoinersDAO.getManchePlayedByUser(id);
        }catch (SQLException e){System.err.println("SQLException in getManchePlayedByUser");
            return 0;
        }
    }

    @Override
    public int getMatchesPlayedByUser(String id) {
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            return mancheJoinersDAO.getMatchesPlayedByUser(id);
        }catch (SQLException e){System.err.println("SQLException in getMatchesPlayedByUser");
            return 0;
        }
    }

    @Override
    public int getObservedManchesByUser(String id) {
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            return mancheJoinersDAO.getObservedManches(id);
        }catch (SQLException e){System.err.println("SQLException in getObservedManchesByUser");
            return 0;
        }
    }

    @Override
    public int getObservedMatchesByUser(String id) {
        if(mancheJoinersDAO == null)
            createMancheJoinersDAO();
        try{
            return mancheJoinersDAO.getObservedMatches(id);
        }catch (SQLException e){System.err.println("SQLException in getObservedMatchesByUser");
            return 0;
        }
    }

    private void createMatchWinnersDAO(){matchWinnersDAO = new MatchWinnersDAOImpl(con);}

    @Override
    public synchronized boolean addMatchWinner(String idMatch, String idPlayer, int amount) {
        if(matchWinnersDAO==null)
            createMatchWinnersDAO();
        try {
            return matchWinnersDAO.addMatchWinner(idMatch, idPlayer, amount);
        } catch (SQLException e) {
            System.err.println("SQLException in addMatchWinner");
            return false;
        }
    }

    @Override
    public int getWonMatchesByUser(String id) {
        if(matchWinnersDAO == null)
            createMatchWinnersDAO();
        try{
            return matchWinnersDAO.getWonMatchesByUser(id);
        }catch (SQLException e){System.err.println("SQLException in getWonMatchesByUser");
            return 0;
        }
    }

    @Override
    public int getAveragePointsWonByUser(String id) {
        if(matchWinnersDAO == null)
            createMatchWinnersDAO();
        try{
            int matchesWon = matchWinnersDAO.getWonMatchesByUser(id);
            if(matchesWon > 0){
                int totalPoints = matchWinnersDAO.getTotalPointsByUser(id);
                return (totalPoints/matchesWon);
            }else
                return 0;
        }catch (SQLException e){System.err.println("SQLException in getAveragePointsWonByUser");
            return 0;
        }
    }
}
