package serverRdF.emailRdF;

/**
 *
 */
public class EmailManager {
    //TODO metodi e commenti javadoc
    private static EmailManager emailManager = null;

    private EmailManager() {
    }

    /**
     * @return emailManager il singleton di tipo EmailManager
     */
    public static EmailManager createEmailManager() {
        if (emailManager == null) {
            emailManager = new EmailManager();
            return emailManager;
        } else
            return emailManager;
    }

    /**
     * @param sub l'oggetto della mail
     * @param txt il corpo del messaggio
     */
    public void sendEmail(String to, String sub, String txt) {
        //TODO
    }

    /**
     * @param email
     */
    public void checkSMTPAccount(String email) {
        //TODO
    }

    /**
     * Questo metdodo permette all'utente di loggarsi
     *
     * @param email    la mail dell'utente
     * @param password la password dell'utente
     */
    public void logIntoAccount(String email, String password) {
        //TODO
    }

    /**
     * Questo metodo permette all'utente di registrarsi
     *
     * @param email    la mail dell'utente
     * @param password la password dell'utente
     */
    public void registerAccount(String email, String password) {
        //TODO
    }
}
