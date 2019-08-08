package serverRdF.dbComm;

import java.time.LocalDateTime;

public interface DBManagerInterface {

    public boolean addMatch(String id, LocalDateTime time);

    //TODO tutti i metodi che servono agli altri manager e a Match. ricordarsi di aggiungere man mano i metodi utilizzati nelle altre classi per non perderli.
}
