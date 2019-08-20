package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PhrasesDAOImpl implements PhrasesDAO {
    private Connection con;
    PhrasesDAOImpl(Connection c){
        this.con=c;
    }
    @Override
    public List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) throws SQLException {
        String query5Phrases = "SELECT * FROM "+PhraseTable+" WHERE "+PhrasePhraseAttribute+" <>" +
                "(SELECT "+PhrasePhraseAttribute+" FROM Manches M JOIN MatchJoiners MJ ON M.id=MJ.id AND M.number = MJ.number " +
                "WHERE idPlayer = '"+idPlayer1+"' OR idPlayer = '"+idPlayer2+"' OR idPlayer = '"+idPlayer3+"'" +
                "GROUP BY "+PhrasePhraseAttribute+" HAVING COUNT(*)=5);";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query5Phrases);
        if(resultSet==null)
            return null;
        ArrayList<PhrasesDTO> pDTO = new ArrayList<>();
        while(resultSet.next())
            pDTO.add(new PhrasesDTO(resultSet.getString(PhraseThemeAttribute), resultSet.getString(PhrasePhraseAttribute)));
        return pDTO;
    }

    @Override
    public boolean addPhrases(ArrayList<PhrasesDTO> phrases) throws SQLException {
       String queryAdd = "INSERT INTO "+PhraseTable+"("+PhraseThemeAttribute+","+PhrasePhraseAttribute+") VALUES ";
       int count=0;
       for(PhrasesDTO phrasesDTO : phrases){
           queryAdd+="('"+phrasesDTO.getTheme()+"','"+phrasesDTO.getPhrase()+"')";
           if(++count<phrases.size())
               queryAdd+=",";
           else
               queryAdd+=";";
       }
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) == phrases.size();
    }

    @Override
    public List<PhrasesDTO> getAllPhrases() throws SQLException {
        String query = "SELECT * FROM "+PhraseTable+";";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);
        if(resultSet==null)
            return null;
        ArrayList<PhrasesDTO> pDTO = new ArrayList<>();
        while(resultSet.next())
            pDTO.add(new PhrasesDTO(resultSet.getString(PhraseThemeAttribute), resultSet.getString(PhrasePhraseAttribute)));
        return pDTO;
    }
}
