package serverRdF.dbComm;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface MatchesDAO {
    //TODO aggiungere metodi necessari
    String MatchTable = "Matches";
    String MatchIdAttribute = "id";
    String MatchDateAttribute = "date";
    String MatchTimeAttribute = "time";

    /**
     *
     * @param matchesDTO riferimento al Data Transfer Object relativo alla partita
     * @return <code>true</code> se la query va a buon fine, altrimenti <code>false</code>
     */
    boolean addMatch(MatchesDTO matchesDTO) throws SQLException;
}