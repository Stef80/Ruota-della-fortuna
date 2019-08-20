package serverRdF.registrationRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.CryptPassword;
import rdFUtil.logging.User;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.UsersDTO;
import serverRdF.emailRdF.EmailManager;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Random;

/**
 * Questa classe gestisce la registrazione dell'utente. I metodi {@link #checkEmail(String)} e {@link #checkNickname(String)} permettono di allegerire i controlli sul metodo principale della classe {@link #signUp(User, Client)}
 */
public class RegistrationManager {
    private DBManager dbManager;
    private EmailManager emailManager;
    private static RegistrationManager registrationManager = null;

    private RegistrationManager(DBManager dbManager, EmailManager emailManager) {
        this.dbManager = dbManager;
        this.emailManager = emailManager;
    }


    /**
     * @param dbManager    il riferimento al manager del db
     * @param emailManager il riferimento al manager delle email
     * @return il singleton della classe.
     */
    public static RegistrationManager createRegistrationManager(DBManager dbManager, EmailManager emailManager) {
        if (registrationManager == null) {
            registrationManager = new RegistrationManager(dbManager, emailManager);
            return registrationManager;
        } else
            return registrationManager;
    }

    /**
     * Registra l'utente nel database. Nel caso non riesca notifica al client l'errore
     *
     * @param form contenente i dati necessari alla registrazione
     * @param c    il riferimento al client
     * @return un riferimento all'oggeto remoto {@link OTPHelper} per l'invio del codice da parte del client
     * @throws RemoteException in caso di errori di connesione al server
     */
    public OTPHelper signUp(User form, Client c) throws RemoteException {
        //TODO la parte di invio della email
        String otp = generateOTP();
        String sub = "Conferma della registrazione a Ruota della Fortuna";
        String text = "Prego inserire il codice: " + otp + " per ultimare la registrazione. Il codice deve essere inserito entro 10 minuti pena l'annullamento della registrazione";
        emailManager.sendEmail(form.getEmail(), sub, text);
        WaitingThread thread = new WaitingThread(c, dbManager, form);
        String cryptedOTP = CryptPassword.encrypt(otp);
        OTPHelperImplementation otpHelper = new OTPHelperImplementation(thread, cryptedOTP);
        thread.start();
        return otpHelper;
    }

    /**
     * Genera un codice numerico di 6 cifre
     *
     * @return il codice OTP random
     */
    private String generateOTP() {
        Random rd = new Random();
        String res = "";
        for (int i = 0; i < 6; i++) {
            res += rd.nextInt(10);
        }
        return res;
    }

    /**
     * Questo metodo controlla se non esiste gia' un utente con la stessa mail
     *
     * @param email L'indirizzo email da controllare
     * @return <code>true</code> se l'indirizzo email non e' stato gia' utilizzato, <code>false</code> altrimenti
     */
    public boolean checkEmail(String email) throws SQLException {
        UsersDTO user = dbManager.getUserByEmail(email);
        if (user != null) {
            return false;
        } else return true;
    }

    /**
     * Questo metodo controlla se non esiste gia' un utente con lo stesso nickname
     *
     * @param nickname il nickname da controllare
     * @return <code>true</code> se il nickname non e' stato gia' utilizzato, <code>false</code> altrimenti
     */
    public boolean checkNickname(String nickname) throws SQLException {
        UsersDTO user = dbManager.getUserByNickname(nickname);
        if (user != null) {
            return false;
        } else return true;
    }
}

