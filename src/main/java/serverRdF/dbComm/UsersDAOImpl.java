package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link UsersDAO}
 */
public class UsersDAOImpl implements UsersDAO {
    private Connection con;
    UsersDAOImpl(Connection c){
        this.con=c;
    }
    @Override
    public boolean addUser(UsersDTO user) throws SQLException {
        String queryAdd = "INSERT INTO "+UserTable+"("+UserIdAttribute+","+UserTipoAttribute+","+UserNameAttribute+","+
                UserSurnameAttribute+","+UserNicknameAttribute+","+UserEmailAttribute+","+UserPasswordAttribute+") " +
                "VALUES ('"+user.getId()+"',"+user.isAdmin()+",'"+user.getName()+"','"+user.getSurname()+"','"
                +user.getNickname()+"','"+user.getEmail()+"','"+user.getPassword()+"');";

        Statement stmt = con.createStatement();
        System.out.println("Provo ad aggiungere");
        boolean res =  stmt.executeUpdate(queryAdd) > 0;
        System.out.println("aggiunto: " + res);
        return res;
    }

    @Override
    public UsersDTO getUserByEmail(String email) throws SQLException {
        String querySearch = "SELECT * FROM "+UserTable+" WHERE "+UserEmailAttribute+" = '"+email+"';";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(querySearch);
        if(resultSet==null)
            return null;
        resultSet.next();
        return new UsersDTO(resultSet.getString(UserIdAttribute),resultSet.getBoolean(UserTipoAttribute),
                resultSet.getString(UserNameAttribute),resultSet.getString(UserSurnameAttribute),
                resultSet.getString(UserNicknameAttribute), email,
                resultSet.getString(UserPasswordAttribute));
    }

    @Override
    public UsersDTO getUserByNickname(String nickname) throws SQLException {
        String querySearch = "SELECT * FROM "+UserTable+" WHERE "+UserNicknameAttribute+" = '"+nickname+"';";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(querySearch);
        if(resultSet==null)
            return null;
        resultSet.next();
        return new UsersDTO(resultSet.getString(UserIdAttribute),resultSet.getBoolean(UserTipoAttribute),
                resultSet.getString(UserNameAttribute),resultSet.getString(UserSurnameAttribute), nickname,
                resultSet.getString(UserEmailAttribute), resultSet.getString(UserPasswordAttribute));
    }

    @Override
    public UsersDTO getUserById(String id) throws SQLException {
        String querySearch = "SELECT * FROM "+UserTable+" WHERE "+UserIdAttribute+" = '"+id+"';";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(querySearch);
        if(resultSet==null)
            return null;
        return new UsersDTO(id,resultSet.getBoolean(UserTipoAttribute), resultSet.getString(UserNameAttribute),
                resultSet.getString(UserSurnameAttribute), resultSet.getString(UserNicknameAttribute),
                resultSet.getString(UserEmailAttribute), resultSet.getString(UserPasswordAttribute));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        String queryDelete = "DELETE FROM "+UserTable+" WHERE "+UserIdAttribute+" = '"+id+"';";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryDelete) > 0;
    }

    @Override
    public List<UsersDTO> getAllAdmin() throws SQLException {
        List<UsersDTO> list = new ArrayList<>();
        String queryGet = "SELECT * FROM "+ UserTable + " WHERE "+UserTipoAttribute + " = 1;";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(queryGet);
        if(resultSet == null){
            return list;
        }else{
            while(resultSet.next()){
                list.add(new UsersDTO(resultSet.getString(UserIdAttribute),resultSet.getBoolean(UserTipoAttribute),
                        resultSet.getString(UserNameAttribute),resultSet.getString(UserSurnameAttribute), resultSet.getString(UserNicknameAttribute),
                        resultSet.getString(UserEmailAttribute), resultSet.getString(UserPasswordAttribute)));
            }
            resultSet.close();
            return list;
        }
    }

    @Override
    public boolean updateUser(UsersDTO user) throws SQLException {
        String queryUpdate = "UPDATE "+UserTable+" SET "+UserNameAttribute+" = '"+user.getName()+"', "+UserSurnameAttribute+" = '"+user.getSurname()+"', "+
                UserNicknameAttribute+" = '"+user.getNickname()+"', "+UserPasswordAttribute+" = '"+user.getPassword()+"' WHERE "+UserIdAttribute+" = '"+user.getId()+"';";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryUpdate) > 0;
    }

    @Override
    public UsersDTO getBestPlayerByManche() throws SQLException {
        String queryBest = "SELECT * FROM " + UserTable + " U JOIN "+MancheWinnersDAO.manchesWinnersTable +" MW ON U.id = MW.idPlayer WHERE "+MancheWinnersDAO.manchesWinnersAmountAttribute+" =" +
                " (SELECT MAX(amount) FROM "+MancheWinnersDAO.manchesWinnersTable+");";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBest);
        if(rs.next()) {
            UsersDTO user = new UsersDTO();
            user.setNickname(rs.getString(UserNicknameAttribute));
            user.setName(rs.getString(UserNameAttribute));
            user.setId(rs.getString(UserIdAttribute));
            user.setSurname(UserSurnameAttribute);
            user.setEmail(UserEmailAttribute);
            return user;
        }else
            return null;
    }

    @Override
    public UsersDTO getBestPlayerByMatch() throws SQLException {
        String queryBest = "SELECT * FROM " + UserTable + " U JOIN "+MatchWinnersDAO.matchWinnersTable +" MW ON U.id = MW.idPlayer WHERE "+MatchWinnersDAO.matchWinnersAmountAttribute+" =" +
                " (SELECT MAX(amount) FROM "+MatchWinnersDAO.matchWinnersTable+");";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBest);
        if(rs.next()) {
            UsersDTO user = new UsersDTO();
            user.setNickname(rs.getString(UserNicknameAttribute));
            user.setName(rs.getString(UserNameAttribute));
            user.setId(rs.getString(UserIdAttribute));
            user.setSurname(UserSurnameAttribute);
            user.setEmail(UserEmailAttribute);
            return user;
        }else
            return null;
    }

    @Override
    public UsersDTO getUserForMoreManchesPlayed() throws SQLException {
        String queryBest = "SELECT "+UserNicknameAttribute+", COUNT("+UserIdAttribute+") AS count FROM " +UserTable+" U JOIN "+MancheJoinersDAO.mancheJoinersTable+" MJ ON U.id = MJ.idPlayer GROUP BY " +
                ""+UserIdAttribute+" ORDER BY count desc";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBest);
        if(rs.next()) {
            UsersDTO user = new UsersDTO();
            user.setNickname(rs.getString(UserNicknameAttribute));
            return user;
        }else
            return null;
    }

    @Override
    public UsersDTO getUserForBestMancheAverage() throws SQLException {
        String queryBest = "SELECT "+UserNicknameAttribute+", AVG("+MancheWinnersDAO.manchesWinnersAmountAttribute+") AS avg FROM " +UserTable+" U JOIN "+MancheWinnersDAO.manchesWinnersTable+" MW ON U.id = MW.idPlayer GROUP BY " +
                ""+UserIdAttribute+" ORDER BY avg desc";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBest);
        if(rs.next()) {
            UsersDTO user = new UsersDTO();
            user.setNickname(rs.getString(UserNicknameAttribute));
            return user;
        }else
            return null;
    }

    @Override
    public UsersDTO getUserForMostLostTurn() throws SQLException{
        String queryBest = "SELECT "+UserNicknameAttribute+", COUNT("+UserIdAttribute+") AS count FROM " +UserTable+" U " +
                "JOIN "+MancheJoinersDAO.mancheJoinersTable+" MW ON U.id = MW.idPlayer " +
                "JOIN "+ManchesDAO.ManchesTable+" MT ON MT.number = MW.number AND MT.id = MW.id " +
                "JOIN " + MovesDAO.MovesTable + " M ON M.number = MT.number AND MT.id = M.idMatch " +
                "WHERE M." + MovesDAO.MovesOutcomeAttribute + " = 0 AND M."+ MovesDAO.MovesMoveTypeAttribute + "<> 'perde' AND M."+ MovesDAO.MovesMoveTypeAttribute + "<> 'passa' " +
                "GROUP BY " +UserIdAttribute +" ORDER BY count desc";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBest);
        if(rs.next()) {
            UsersDTO user = new UsersDTO();
            user.setNickname(rs.getString(UserNicknameAttribute));
            return user;
        }else
            return null;
    }

    @Override
    public UsersDTO getUserForMostLosses() throws SQLException{
        String queryBest = "SELECT "+UserNicknameAttribute+", COUNT("+UserIdAttribute+") AS count FROM " +UserTable+" U " +
                "JOIN "+MancheJoinersDAO.mancheJoinersTable+" MW ON U.id = MW.idPlayer " +
                "JOIN "+ManchesDAO.ManchesTable+" MT ON MT.number = MW.number AND MT.id = MW.id " +
                "JOIN " + MovesDAO.MovesTable + " M ON M.number = MT.number AND MT.id = M.idMatch" +
                "WHERE M."+ MovesDAO.MovesMoveTypeAttribute + " = 'perde' " +
                "GROUP BY " +UserIdAttribute +" ORDER BY count desc";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBest);
        if(rs.next()) {
            UsersDTO user = new UsersDTO();
            user.setNickname(rs.getString(UserNicknameAttribute));
            return user;
        }else
            return null;
    }
}
