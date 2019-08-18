package serverRdF.dbComm;

import java.sql.SQLException;

public interface MatchesDAO {
    //TODO aggiungere metodi necessari
    String MatchTable = "Matches";
    String MatchIdAttribute = "id";
    String MatchDateAttribute = "date";
    String MatchTimeAttribute = "time";

    /**
     * Questo metodo permette di aggiungere una nuova partita all'interno del database
     *
     * @param matchesDTO riferimento al Data Transfer Object relativo alla partita
     * @return <code>true</code> se la query va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addMatch(MatchesDTO matchesDTO) throws SQLException;


    /**
     * Questo metodo cancella una partita dal database
     *
     * @param idMatch id del match da cancellare
     * @return <code>true</code> se l'eliminazione va a buon fine, <code>false</code> altrimenti
     * @throws SQLException
     */
    boolean deleteMatch(String idMatch) throws SQLException;
}