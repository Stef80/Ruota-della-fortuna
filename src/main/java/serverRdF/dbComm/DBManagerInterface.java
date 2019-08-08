package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.time.LocalDateTime;

public interface DBManagerInterface {

    //i metodi ritornano false se non riescono a fare le query (per esempio a causa di SQLException) o null quando la query ritorna una tabella vuota. negli altri casi ritorna true/riferimento

    public boolean addMatch(String id, LocalDateTime time);

    public boolean addUser(User user);

    public UsersDTO getUser(boolean email, String unique);

    //TODO tutti i metodi che servono agli altri manager e a Match. ricordarsi di aggiungere man mano i metodi utilizzati nelle altre classi per non perderli.
}
