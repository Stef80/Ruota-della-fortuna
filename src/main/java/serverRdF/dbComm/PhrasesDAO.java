package serverRdF.dbComm;

import java.util.ArrayList;
import java.util.List;

public interface PhrasesDAO {
    //TODO aggiungere metodi necessari
    String PhraseTable = "Phrases";
    String PhrasePhraseAttribute = "phrase";
    String PhraseThemeAttribute = "theme";

    List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3);
    boolean addPhrase(ArrayList<PhrasesDTO> phrases);
    List<PhrasesDTO> getAllPhrases();
}