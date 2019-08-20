package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MancheJoinersDAOImpl implements MancheJoinersDAO {
    private Connection con;
    MancheJoinersDAOImpl(Connection c){con = c;}

    public boolean addMancheJoiner(String idMatch, int numManche, String idPlayer, boolean observer) throws SQLException {
        String queryAdd = "INSERT INTO "+mancheJoinersTable+"("+mancheJoinersIdMatchAttribute+","+mancheJoinersNumMancheAttribute+","+mancheJoinersIdPlayerAttribute+","+mancheJoinersObserverAttribute+") " +
                "VALUES ('"+idMatch+"',"+numManche+","+idPlayer+","+(observer? 1 : 0)+");";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }
}
