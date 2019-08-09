package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PhrasesDAO {
    //TODO aggiungere metodi necessari
    String PhraseTable = "Phrases";
    String PhrasePhraseAttribute = "phrase";
    String PhraseThemeAttribute = "theme";

    /**
     * @param idPlayer1 id del primo concorrente iscritto alla partita
     * @param idPlayer2 id del secondo concorrente iscritto alla partita
     * @param idPlayer3 id del terzo concorrente iscritto alla partita
     * @return la lista delle cinque frasi, con relativo tema, se trovate, altrimenti null
     */
    List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) throws SQLException;

    /**
     *
     * @param phrases lista delle frasi, con relativo tema, da aggiungere nel database
     * @return true se la query è andata a buon fine, false altrimenti
     */
    boolean addPhrases(ArrayList<PhrasesDTO> phrases) throws SQLException;

    /**
     * @return la lista di tutte le frasi, con relativo tema, presenti nel database. In caso di problemi si restituisce null
     */
    List<PhrasesDTO> getAllPhrases() throws SQLException;
}