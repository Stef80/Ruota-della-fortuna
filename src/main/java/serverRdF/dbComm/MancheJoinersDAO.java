package serverRdF.dbComm;

import java.sql.SQLException;

public interface MancheJoinersDAO {
    String mancheJoinersTable = "MancheJoiners";
    String mancheJoinersIdMatchAttribute = "id";
    String mancheJoinersNumMancheAttribute = "number";
    String mancheJoinersIdPlayerAttribute = "idPlayer";
    String mancheJoinersObserverAttribute = "observer";

    boolean addMancheJoiner(String idMatch, int numManche, String idPlayer, boolean observer) throws SQLException;

    int getManchePlayedByUser(String id) throws SQLException;

    int getMatchesPlayedByUser(String id) throws SQLException;
}
