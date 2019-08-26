package rdFUtil.logging;

import java.io.Serializable;

/**
 * Classe che contiene tutti i dati necessari al'accesso alla piattaforma. I metodi permettono la modifica e l'accesso a tali dati
 */
public class Login implements Serializable {
    public static final long serialVersionUID = 1L;

    protected String passwordC;
    protected String email;

    public Login(String password, String mail) {
        passwordC = CryptPassword.encrypt(password);
        email = mail;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return la password criptata dell'utente
     */
    public String getPasswordC() {
        return passwordC;
    }

    /**
     * @return la mail dell'utente
     */
    public String getEmail() {
        return email;
    }
}
