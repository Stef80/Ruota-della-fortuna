package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MancheWinnersDAOImpl implements MancheWinnersDAO {
    private Connection con;

    MancheWinnersDAOImpl(Connection c){
        con = c;
    }

    public boolean addMancheWinner(String idPlayer, ManchesDTO manche, int amount) throws SQLException{
        String queryAdd = "INSERT INTO "+manchesWinnersTable+"("+manchesWinnersidAttribute+","+manchesWinnersNumberAttribute+","+manchesWinnersidPlayerAttribute+","+manchesWinnersAmountAttribute+") " +
                "VALUES ('"+manche.getMatch().getId()+"',"+manche.getNumber()+",'"+idPlayer+"',"+amount+");";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }
}
