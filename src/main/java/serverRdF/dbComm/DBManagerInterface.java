package serverRdF.dbComm;

import rdFUtil.logging.User;
import serverRdF.matchRdF.Move;

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
     * Questo metodo ritorna tutti gli admin registrati alla piattaforma
     *
     * @return La lista di tutti gli admin registrati
     * @throws SQLException
     */
    List<UsersDTO> getAllAdmin() throws SQLException;

    /**
     * Questo metodo permette di eliminare un utente presente nel database
     * @param id id dell'utente
     * @return <code>true</code> se l'inserimento e' andato a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean deleteUser(String id) throws SQLException;

    /**
     * Questo metodo controlla che la mail inserita e la password di un utente siano corrette e che l'utente sia o meno un admin
     *
     * @param email la mail dell'utente
     * @param password la password dell'utente
     * @param admin se e' un admin
     * @return <code>0</code> se i dati sono tutti esatti, <code>-1</code> se la mail non esiste o la mail esiste ma la password e' errata e <code>1</code> se email e password sono corrette
     * ma l'utente non e' un admin quando <code>admin = true</code> o viceversa
     * @throws SQLException
     */
    int checkLogin(String email, String password, boolean admin) throws SQLException;

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
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addPhrases(ArrayList<PhrasesDTO> phrases) throws SQLException;

    /**
     * Questo metodo fornisce la lista delle frasi e temi presenti nella base di dati
     *
     * @return lista delle frasi, e relativi temi, oppure in caso di errori <code>null</code>
     * @throws SQLException
     */
    List<PhrasesDTO> getAllPhrases() throws SQLException;


    /**
     * Questo metodo cancella una partita dal database
     *
     * @param idMatch id del match da cancellare
     * @return <code>true</code> se l'eliminazione va a buon fine, <code>false</code> altrimenti
     * @throws SQLException
     */
    boolean deleteMatch(String idMatch) throws SQLException;

    /**
     * Questo metodo aggiunge nel database una mossa
     *
     * @param move la mossa da inserire
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addMove(Move move) throws SQLException;


    /**
     * Questo metodo aggiunge nel database una manche giocata
     *
     * @param manche la manche da inserire
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addManche(ManchesDTO manche) throws SQLException;

    /**
     * Questo metodo aggiunge nel database il vincitore di una manche giocata
     *
     * @param idPlayer l'id del vincitore
     * @param manche la manche giocata
     * @param amount il punteggio ottenuto nella manche
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addMancheWinner(String idPlayer, ManchesDTO manche, int amount) throws SQLException;

    /**
     * Questo metodo collega un utente ad una manche che ha osservato o giocato
     *
     * @param idMatch id del match giocato/osservato
     * @param numManche numero della manche
     * @param userId id dell'utente
     * @param observer <code>true</code> se l'utente era un osservatore, <code>false</code> altrimenti
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     */
    boolean addMancheJoiner(String idMatch, int numManche, String userId, boolean observer);

    /**
     * Questo metodo aggiunge al database il vincitore di una partita
     *
     * @param idMatch id del match vinto
     * @param idPlayer l'id del vincitore
     * @param amount il punteggio ottenuto
     * @return <code>true</code> se l'inserimento va a buon fine, altrimenti <code>false</code>
     */
    boolean addMatchWinner(String idMatch, String idPlayer, int amount);

    /**
     * Questo metodo modifica una riga della tabella Users
     *
     * @param user L'utente a cui e' stato modificato un valore (Notare che la chiave primaria non viene mai cambiata)
     * @return <code>true</code> se la modifica va a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean updateUser(UsersDTO user) throws SQLException;

    /**
     * Questo metodo permette di individuare la mossa che ha fatto guadagnare la maggiore quantita' di punti
     *
     * @return Un oggetto di tipo {@link UsersDTO}
     * @throws SQLException
     */
    MovesDTO getBestMove();
    //TODO tutti i metodi che servono agli altri manager e a Match. ricordarsi di aggiungere man mano i metodi utilizzati nelle altre classi per non perderli.
}
