package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface DBManagerInterface {

    //i metodi ritornano false se non riescono a fare le query (per esempio a causa di SQLException) o null quando la query ritorna una tabella vuota. negli altri casi ritorna true/riferimento

    boolean addMatch(String id, LocalDateTime time);

    boolean addUser(User user);

    UsersDTO getUser(boolean email, String unique);

    boolean checkLogin(String email, String password) throws SQLException;

    List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) throws SQLException;

    boolean addPhrases(ArrayList<String> phrases, ArrayList<String> themes) throws SQLException;

    List<PhrasesDTO> getAllPhrases() throws SQLException;

    //TODO tutti i metodi che servono agli altri manager e a Match. ricordarsi di aggiungere man mano i metodi utilizzati nelle altre classi per non perderli.
}
