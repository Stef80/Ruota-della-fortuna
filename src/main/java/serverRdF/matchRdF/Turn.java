package serverRdF.matchRdF;

        import serverRdF.dbComm.DBManager;
        import serverRdF.dbComm.ManchesDTO;
        import serverRdF.dbComm.MovesDTO;

        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

public class Turn {

    private List<Move> moves;
    private Manche manche;

    public Turn(Manche manche){
        moves = new ArrayList<>();
        this.manche = manche;
    }

    //TODO

    public boolean saveMoves(DBManager dbManager){
        boolean success = true;
        for(MovesDTO move : moves){
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

    public void addMove(DBManager dbManager, String player, String moveType, int outcome){
        MovesDTO move = new MovesDTO();
        try {
            move.setPlayer(dbManager.getUserByNickname(player));
        }catch(SQLException e){
            e.getErrorCode();
        }
        move.setMoveType(moveType);
        move.setOutcome(outcome);
        move.setManche(new ManchesDTO(manche.getNumManche(),));
    }
    //TODO metodo per svuotare la lista e salvare nel DB
}
