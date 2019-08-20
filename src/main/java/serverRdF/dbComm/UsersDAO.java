package serverRdF.dbComm;

import java.sql.SQLException;

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
}
