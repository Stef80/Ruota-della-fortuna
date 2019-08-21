package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchWinnersDAOImpl implements MatchWinnersDAO {
    private Connection con;
    MatchWinnersDAOImpl(Connection c){con = c;}

    public boolean addMatchWinner(String idMatch, String idPlayer, int amount) throws SQLException {
        String queryAdd = "INSERT INTO "+matchWinnersTable+"("+matchWinnersIdMatchAttribute+","+matchWinnersidPlayerAttribute+","+matchWinnersAmountAttribute+") " +
                "VALUES ('"+idMatch+"','"+idPlayer+"',"+amount+");";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }
}
