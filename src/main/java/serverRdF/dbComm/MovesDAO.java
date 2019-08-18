package serverRdF.dbComm;

import java.sql.SQLException;

public interface MovesDAO {
    /**
     * Questo metodo aggiunge nel database una mossa
     *
     * @param move la mossa da inserire
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addMove(MovesDTO move) throws SQLException;
}
