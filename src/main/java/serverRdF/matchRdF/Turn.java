package serverRdF.matchRdF;

        import serverRdF.dbComm.DBManager;
        import serverRdF.dbComm.ManchesDTO;
        import serverRdF.dbComm.MovesDTO;

        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;


/**
 * Questa classe contiene tutte le mosse eseguite in un turno
  */
public class Turn {

    private List<Move> moves;
    private Manche manche;

    public Turn(Manche manche){
        moves = new ArrayList<>();
        this.manche = manche;
    }

    /**
     * Questo metodo si occupa di salvare tutte le mosse eseguite da un giocatore in suo turno alla fine della manche
     *
     * @param dbManager riferimento all'oggetto di tipo {@link DBManager} per l'accesso al database
     * @return <code>true</code> se l'operazione va a buon fine, <code>false</code> altrimenti
     */
    public boolean saveMoves(DBManager dbManager){
        boolean success = true;
        for(Move move : moves){
            try {
                if(success == true) {
                    success = dbManager.addMove(move);
                }else{
                    dbManager.addMove(move);
                }
            }catch(SQLException e){
                success = false;
            }
        }
        return success;
    }

    /**
     * Questo metodo aggiunge una mossa appena eseguita all lista.
     *
     * @param idPlayer l'id del giocatore che ha eseguito la mossa
     * @param moveType il tipo di mossa eseguita
     * @param outcome il risultato della mossa
     */
    public void addMove(String idPlayer, String moveType, int outcome){
        Move move = new Move();
        move.setIdMatch(manche.getMatch());
        move.setNumManche(manche.getNumManche());
        move.setPlayer(idPlayer);
        move.setMoveType(moveType);
        move.setOutCome(outcome);

        moves.add(move);
    }
}
