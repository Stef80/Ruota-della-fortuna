package serverRdF.emailRdF;

import serverRdF.ServerImplementation;

import javax.mail.MessagingException;

public class EmailManager {
    //TODO metodi e commenti javadoc
    private static EmailManager emailManager = null;
    private String email;
    private String password;

    private EmailManager(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * @return il singleton di tipo {@link EmailManager}
     */
    public static EmailManager createEmailManager(String email, String password) {
        if (emailManager == null) {
            emailManager = new EmailManager(email,password);
            return emailManager;
        } else
            return emailManager;
    }

    /**
     * @param to  il destinatario
     * @param sub l'oggetto della mail
     * @param txt il corpo del messaggio
     */
    public void sendEmail(String to, String sub, String txt) {
        try {
            EmailSender.sendUninsubriaEmail(email,password,to,sub,txt);
        } catch (MessagingException e) {
            ServerImplementation.serverError(null);
        }
    }

    /**
     * Questo metdodo permette all'utente di loggarsi
     *
     * @param email    la mail dell'utente
     * @param password la password dell'utente
     */
    public boolean logIntoAccount(String email, String password) {
        
    }
}
