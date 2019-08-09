package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class MatchesDAOImpl implements MatchesDAO {
    private Connection con;
    MatchesDAOImpl(Connection c){
        this.con=c;
    }

    @Override
    public boolean addMatch(MatchesDTO matchesDTO) throws SQLException {
        String queryAdd = "INSERT INTO "+MatchTable+"("+MatchIdAttribute+","+MatchDateAttribute+","+MatchTimeAttribute+") " +
                "VALUES ('"+matchesDTO.getId()+"',"+matchesDTO.getDate()+","+matchesDTO.getTime()+");";
        Statement stmt = con.createStatement();
        if(stmt.executeUpdate(queryAdd)>0)
            return true;
        else
            return false;
    }
}
