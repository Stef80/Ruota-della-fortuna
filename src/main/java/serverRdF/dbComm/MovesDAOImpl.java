package serverRdF.dbComm;

import serverRdF.matchRdF.Move;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MovesDAOImpl implements MovesDAO {
    private Connection con;

    MovesDAOImpl(Connection c){
        con = c;
    }

    public boolean addMove(Move move) throws SQLException{
        String queryAdd = "INSERT INTO " + MovesTable + "(" + MovesIdPlayerAttribute + "," + MovesMoveTypeAttribute + "," + MovesOutcomeAttribute + "," + MovesIdMatchAttribute + "," + MovesMancheNumberAttribute + ") " +
                            "VALUES ('" + move.getPlayer() + "','" + move.getMoveType() + "'," + move.getOutCome() + ",'" + move.getIdMatch() + "'," + move.getNumManche() + ")";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }
}
