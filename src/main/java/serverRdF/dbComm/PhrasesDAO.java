package serverRdF.dbComm;

import java.util.ArrayList;
import java.util.List;

public interface PhrasesDAO {
    public List<PhrasesDTO> get5Phrases(int idPlayer1, int idPlayer2, int idPlayer3);
    public void addPhrase(ArrayList<PhrasesDTO> phrases);
    public List<PhrasesDTO> getAllPhrases();
}
