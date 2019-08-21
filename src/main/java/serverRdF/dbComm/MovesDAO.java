package serverRdF.dbComm;

import serverRdF.matchRdF.Move;

import java.sql.SQLException;

public interface MovesDAO {
    String MovesTable = "Moves";
    String MovesIdPlayerAttribute = "id";
    String MovesMoveTypeAttribute = "moveType";
    String MovesOutcomeAttribute = "outcome";
    String MovesIdMatchAttribute  = "idMatch";
    String MovesMancheNumberAttribute = "number";

    /**
     * Questo metodo aggiunge nel database una mossa
     *
     * @param move la mossa da inserire
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addMove(Move move) throws SQLException;

    /**
     * Questo metodo permette di individuare la mossa che ha fatto guadagnare la maggiore quantita' di punti
     *
     * @return Un oggetto di tipo {@link UsersDTO}
     * @throws SQLException
     */
    public MovesDTO getBestMove() throws SQLException;

    public int getAverageMovesPerManche(int numManche) throws SQLException;
}
