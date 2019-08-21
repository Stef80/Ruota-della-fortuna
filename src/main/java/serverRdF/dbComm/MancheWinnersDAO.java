package serverRdF.dbComm;

import java.sql.SQLException;

public interface MancheWinnersDAO {
    String manchesWinnersTable = "MancheWinners";
    String manchesWinnersidAttribute = "id";
    String manchesWinnersNumberAttribute = "number";
    String manchesWinnersidPlayerAttribute = "idPlayer";
    String manchesWinnersAmountAttribute = "amount";

    boolean addMancheWinner(String idPlayer, ManchesDTO manche, int amount) throws SQLException;

    int getNumWinnedManches() throws SQLException;
}
