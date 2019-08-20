package serverRdF.dbComm;

import rdFUtil.logging.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface DBManagerInterface {

    //i metodi ritornano false se non riescono a fare le query (per esempio a causa di SQLException) o null quando la query ritorna una tabella vuota. negli altri casi ritorna true/riferimento

    /**
     * Questo metodo permette di aggiungere una nuova partita nel database
     *
     * @param id   e' l'identificativo della partita
     * @param time rappresenta la data e ora di creazione della partita
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addMatch(String id, LocalDateTime time) throws SQLException;

    /**
     * Questo metodo permette di aggiungere un utente all'interno del database
     * @param user riferimento all'utente
     * @param isAdmin <code>true</code> se l'utente è un amministratore, <code>false</code> se e' un player
     * @return <code>true</code> se l'inserimento e' andato a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addUser(User user, boolean isAdmin) throws SQLException;

    /**
     * Questo metodo permette di ottenere le informazioni di un utente conoscendo la sua email
     * @param email email dell'utente
     * @return riferimento all'utente trovato (null se non viene trovato)
     * @throws SQLException
     */
    UsersDTO getUserByEmail(String email) throws SQLException;

    /**
     * Questo metodo permette di ottenere le informazioni di un utente conoscendo il suo nickname
     * @param nickname nickname dell'utente
     * @return riferimento all'utente trovato (null se non viene trovato)
     * @throws SQLException
     */
    UsersDTO getUserByNickname(String nickname) throws SQLException;

    /**
     * Questo metodo permette di ottenere le informazioni di un utente conoscendo il suo id
     * @param id id dell'utente
     * @return riferimento all'utente trovato (null se non viene trovato)
     * @throws SQLException
     */
    UsersDTO getUserById(String id) throws SQLException;

    /**
     * Questo metodo permette di eliminare un utente presente nel database
     * @param id id dell'utente
     * @return <code>true</code> se l'inserimento e' andato a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean deleteUser(String id) throws SQLException;

    boolean checkLogin(String email, String password) throws SQLException;

    /**
     * Questo metodo si occupa di cercare all'interno del database cinque frasi che i tre concorrenti non hanno mai
     * visto sia in veste di osservatori che di concorrenti
     *
     * @param idPlayer1 id del primo concorrente iscritto alla partita
     * @param idPlayer2 id del secondo concorrente iscritto alla partita
     * @param idPlayer3 id del terzo concorrente iscritto alla partita
     * @return la lista delle cinque frasi, con relativo tema, se trovate, altrimenti <code>null</code>
     * @throws SQLException
     */
    List<PhrasesDTO> get5Phrases(String idPlayer1, String idPlayer2, String idPlayer3) throws SQLException;

    /**
     * Questo metodo permette di aggiungere un insieme di frasi e temi all'interno del database
     *
     * @param phrases lista delle frasi che si vogliono inserire nella base di dati
     * @param themes  lista dei temi, relativi alle frasi, che si vogliono inserire nella base di dati
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addPhrases(ArrayList<String> phrases, ArrayList<String> themes) throws SQLException;

    /**
     * Questo metodo fornisce la lista delle frasi e temi presenti nella base di dati
     *
     * @return lista delle frasi, e relativi temi, oppure in caso di errori <code>null</code>
     * @throws SQLException
     */
    List<PhrasesDTO> getAllPhrases() throws SQLException;

    //TODO tutti i metodi che servono agli altri manager e a Match. ricordarsi di aggiungere man mano i metodi utilizzati nelle altre classi per non perderli.
}
