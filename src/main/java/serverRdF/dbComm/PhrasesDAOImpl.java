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
    /**
     * @param idPlayer1 id del primo concorrente iscritto alla partita
     * @param idPlayer2 id del secondo concorrente iscritto alla partita
     * @param idPlayer3 id del terzo concorrente iscritto alla partita
     * @return la lista delle cinque frasi, con relativo tema, se trovate, altrimenti null
     */
    @Override
    public List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) throws SQLException {
        //TODO rendere costanti attributi e tabelle Manches e MatchJoiner
        String query5Phrases = "SELECT * FROM "+PhraseTable+" WHERE "+PhrasePhraseAttribute+" <>" +
                "(SELECT "+PhrasePhraseAttribute+" FROM Manches M JOIN MatchJoiners MJ ON M.id=MJ.id AND M.number = MJ.number " +
                "WHERE idPlayer = '"+idPlayer1+"' OR idPlayer = '"+idPlayer2+"' OR idPlayer = '"+idPlayer3+"' HAVING COUNT(*)=5);";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query5Phrases);
        if(resultSet==null)
            return null;
        ArrayList<PhrasesDTO> pDTO = new ArrayList<>();
        while(resultSet.next())
            pDTO.add(new PhrasesDTO(resultSet.getString(PhraseThemeAttribute), resultSet.getString(PhrasePhraseAttribute)));
        return pDTO;
    }

    /**
     *
     * @param phrases lista delle frasi, con relativo tema, da aggiungere nel database
     * @return true se la query è andata a buon fine, false altrimenti
     */
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
        if(stmt.executeUpdate(queryAdd)==phrases.size())
            return true;
        else
            return false;
    }

    /**
     * @return la lista di tutte le frasi, con relativo tema, presenti nel database. In caso di problemi si restituisce null
     */
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
