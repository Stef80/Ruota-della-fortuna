package serverRdF.dbComm;

import java.sql.SQLException;

public interface MatchWinnersDAO {
    String matchWinnersTable = "MatchWinners";
    String matchWinnersIdMatchAttribute  = "id";
    String matchWinnersidPlayerAttribute = "idPlayer";
    String matchWinnersAmountAttribute = "amount";

    boolean addMatchWinner(String idMatch, String idPlayer, int amount) throws SQLException;
}
