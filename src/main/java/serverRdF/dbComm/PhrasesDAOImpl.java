package serverRdF.dbComm;

import java.util.ArrayList;
import java.util.List;

public class PhrasesDAOImpl implements PhrasesDAO {
    //TODO connessione con database
    static final String PhraseTable = "Phrases";
    static final String PhrasePhraseAttribute = "phrase";
    static final String PhraseThemeAttribute = "theme";

    @Override
    public List<PhrasesDTO> get5Phrases(int idPlayer1, int idPlayer2, int idPlayer3) {
        //TODO rendere costanti attributi e tabelle Manches e MatchJoiner
        String query5Phrases = "SELECT * FROM "+PhraseTable+" WHERE "+PhrasePhraseAttribute+" <>" +
                "(SELECT "+PhrasePhraseAttribute+" FROM Manches M JOIN MatchJoiners MJ ON M.id=MJ.id AND M.number = MJ.number " +
                "WHERE idPlayer = "+idPlayer1+" OR idPlayer = "+idPlayer2+" OR idPlayer = "+idPlayer3+"HAVING COUNT(*)=5);";
        return null;
    }

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

    @Override
    public List<PhrasesDTO> getAllPhrases() {
        String query = "SELECT * FROM "+PhraseTable+";";
        return null;
    }
}
