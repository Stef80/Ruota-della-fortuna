package serverRdF.emailRdF;

/**
 *
 */
public class EmailManager {
    //TODO metodi e commenti javadoc
    private static EmailManager emailManager = null;
    private EmailManager(){

    }
    /**
     * @return matchManager il singleton di tipo EmailManager
     */
    public static EmailManager createMatchManager() {
        if (emailManager == null) {
            emailManager = new EmailManager();
            return emailManager;
        } else
            return emailManager;
    }
    /**
     * @param obj
     * @param txt
     */
    public void sendEmail(String obj, String txt){
        //TODO
    }
    /**
     *
     * @param email
     */
    public void checkSMTPAccount(String email){
        //TODO
    }
    /**
     *
     * @param email
     * @param password
     */
    public void logIntoAccount(String email, String password){
        //TODO
    }
    /**
     *
     * @param email
     * @param password
     */
    public void registerAccount(String email, String password){
        //TODO
    }
}
