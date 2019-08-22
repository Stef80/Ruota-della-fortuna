package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MancheJoinersDAOImpl implements MancheJoinersDAO {
    private Connection con;
    MancheJoinersDAOImpl(Connection c){con = c;}

    @Override
    public boolean addMancheJoiner(String idMatch, int numManche, String idPlayer, boolean observer) throws SQLException {
        String queryAdd = "INSERT INTO "+mancheJoinersTable+"("+mancheJoinersIdMatchAttribute+","+mancheJoinersNumMancheAttribute+","+mancheJoinersIdPlayerAttribute+","+mancheJoinersObserverAttribute+") " +
                "VALUES ('"+idMatch+"',"+numManche+",'"+idPlayer+"',"+(observer? 1 : 0)+");";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }

    @Override
    public int getManchePlayedByUser(String id) throws SQLException {
        String queryGet = "SELECT COUNT(*) AS count FROM "+mancheJoinersTable+" WHERE "+mancheJoinersIdPlayerAttribute+" = '"+id+"' AND "+mancheJoinersObserverAttribute+" = 0;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryGet);
        return rs.getInt("count");
    }

    @Override
    public int getMatchesPlayedByUser(String id) throws SQLException{
        String queryGet = "SELECT COUNT(*) AS count FROM "+mancheJoinersTable+" WHERE "+mancheJoinersIdPlayerAttribute+" = '"+id+"' AND "+mancheJoinersObserverAttribute+" = 0 GROUP BY "+mancheJoinersIdMatchAttribute+";";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryGet);
        return rs.getInt("count");
    }
}
