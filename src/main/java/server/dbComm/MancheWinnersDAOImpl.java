package server.dbComm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementazione dell'interfaccia {@link MancheWinnersDAO}
 */
public class MancheWinnersDAOImpl implements MancheWinnersDAO {
    private Connection con;

    MancheWinnersDAOImpl(Connection c){
        con = c;
    }

    @Override
    public boolean addMancheWinner(String idPlayer, ManchesDTO manche, int amount) throws SQLException{
        String queryAdd = "INSERT INTO "+manchesWinnersTable+"("+manchesWinnersidAttribute+","+manchesWinnersNumberAttribute+","+manchesWinnersidPlayerAttribute+","+manchesWinnersAmountAttribute+") " +
                "VALUES ('"+manche.getMatch().getId()+"',"+manche.getNumber()+",'"+idPlayer+"',"+amount+");";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }

    @Override
    public int getNumWinnedManches() throws SQLException{
        String queryGet = "SELECT COUNT(*) AS count FROM "+manchesWinnersTable;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryGet);
        if(rs.next()) {
            return rs.getInt("count");
        }else
            return 0;
    }

    @Override
    public int getMancheWonByUser(String id) throws SQLException {
        String queryGet = "SELECT COUNT(*) AS count FROM "+manchesWinnersTable+" WHERE "+manchesWinnersidPlayerAttribute+" = '"+id+"';";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryGet);
        if(rs.next()) {
            return rs.getInt("count");
        }else
            return 0;
    }
}
