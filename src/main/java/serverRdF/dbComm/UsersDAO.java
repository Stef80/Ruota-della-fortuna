package serverRdF.dbComm;

import java.sql.SQLException;
import java.util.List;

public interface UsersDAO {
    //TODO aggiungere metodi necessari
    String UserTable = "Users";
    String UserIdAttribute = "id";
    String UserTipoAttribute = "tipo";
    String UserNameAttribute  = "name";
    String UserSurnameAttribute = "surname";
    String UserNicknameAttribute = "nickname";
    String UserEmailAttribute = "email";
    String UserPasswordAttribute = "password";

    /**
     * Questo metodo permette di aggiungere un utente all'interno del database
     * @param user riferimento all'utente
     * @return <code>true</code> se l'inserimento e' andato a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean addUser(UsersDTO user) throws SQLException;

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

    /**
     * Questo metodo permette di ottenere la lista contenente tutti gli admin
     *
     * @return La lista di utenti registrati come amministratori
     * @throws SQLException
     */
    List<UsersDTO> getAllAdmin() throws SQLException;

    /**
     * Questo metodo permette di modificare una riga della tabella Users
     * @param user riferimento all'utente a cui e' stato modificato un valore
     * @return <code>true</code> se la modifica e' andata a buon fine, altrimenti <code>false</code>
     * @throws SQLException
     */
    boolean updateUser(UsersDTO user) throws SQLException;
}
