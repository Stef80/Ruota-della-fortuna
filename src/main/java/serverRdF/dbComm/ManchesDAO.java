package serverRdF.dbComm;

import java.sql.SQLException;

public interface ManchesDAO {
    String ManchesTable = "Manches";
    String ManchesNumberAttribute = "number";
    String ManchesIdAttribute = "id";
    String ManchesPhraseAttribute = "Phrases";

    boolean addManche(ManchesDTO manche) throws SQLException;
}
