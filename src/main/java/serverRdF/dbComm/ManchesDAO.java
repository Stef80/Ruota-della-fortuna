package serverRdF.dbComm;

import java.sql.SQLException;

public interface ManchesDAO {
    String ManchesTable = "Manches";
    String ManchesNumberAttribute = "number";
    String ManchesIdAttribute = "id";
    String ManchesPhraseAttribute = "Phrases";

    /**
     * Questo metodo aggiunge nel database una manche
     *
     * @param manche la manche da inserire
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addManche(ManchesDTO manche) throws SQLException;
}
