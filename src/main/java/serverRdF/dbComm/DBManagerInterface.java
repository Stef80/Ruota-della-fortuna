package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface DBManagerInterface {

    //i metodi ritornano false se non riescono a fare le query (per esempio a causa di SQLException) o null quando la query ritorna una tabella vuota. negli altri casi ritorna true/riferimento

    public boolean addMatch(String id, LocalDateTime time);

    public boolean addUser(User user);

    public UsersDTO getUserByEmail(String email);

    public UsersDTO getUserByNickname(String nickname);

    public UsersDTO getUserById(String id);

    public boolean checkLogin(String email, String password) throws SQLException;

    public List<PhrasesDTO> get5Phrases(int idPlayer1, int idPlayer2, int idPlayer3);

    public boolean addPhrase(ArrayList<PhrasesDTO> phrases);

    public List<PhrasesDTO> getAllPhrases();

    //TODO tutti i metodi che servono agli altri manager e a Match. ricordarsi di aggiungere man mano i metodi utilizzati nelle altre classi per non perderli.
}
