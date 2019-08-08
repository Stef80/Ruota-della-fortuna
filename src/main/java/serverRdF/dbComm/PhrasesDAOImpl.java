package serverRdF.dbComm;

import java.util.ArrayList;
import java.util.List;

public class PhrasesDAOImpl implements PhrasesDAO {
    //TODO connessione con database
    static final String PhraseTable = "Phrases";
    static final String PhrasePhraseAttribute = "phrase";
    static final String PhraseThemeAttribute = "theme";

    /**
     * @param idPlayer1 id del primo concorrente iscritto alla partita
     * @param idPlayer2 id del secondo concorrente iscritto alla partita
     * @param idPlayer3 id del terzo concorrente iscritto alla partita
     * @return la lista delle cinque frasi, con relativo tema, se trovate, altrimenti null
     */
    @Override
    public List<PhrasesDTO> get5Phrases(int idPlayer1, int idPlayer2, int idPlayer3) {
        //TODO rendere costanti attributi e tabelle Manches e MatchJoiner
        String query5Phrases = "SELECT * FROM "+PhraseTable+" WHERE "+PhrasePhraseAttribute+" <>" +
                "(SELECT "+PhrasePhraseAttribute+" FROM Manches M JOIN MatchJoiners MJ ON M.id=MJ.id AND M.number = MJ.number " +
                "WHERE idPlayer = "+idPlayer1+" OR idPlayer = "+idPlayer2+" OR idPlayer = "+idPlayer3+"HAVING COUNT(*)=5);";
        return null;
    }

    /**
     * @param phrases lista delle frasi, con relativo tema, da aggiungere nel database
     */
    @Override
    public void addPhrase(ArrayList<PhrasesDTO> phrases) {
       String queryAdd = "INSERT INTO "+PhraseTable+"("+PhraseThemeAttribute+","+PhrasePhraseAttribute+") VALUES ";
       int count=0;
       for(PhrasesDTO phrasesDTO : phrases){
           queryAdd+="('"+phrasesDTO.getTheme()+"',"+phrasesDTO.getPhrase()+"')";
           if(++count<phrases.size())
               queryAdd+=",";
           else
               queryAdd+=";";
       }
    }

    /**
     * @return la lista di tutte le frasi, con relativo tema, presenti nel database
     */
    @Override
    public List<PhrasesDTO> getAllPhrases() {
        String query = "SELECT * FROM "+PhraseTable+";";
        return null;
    }
}
