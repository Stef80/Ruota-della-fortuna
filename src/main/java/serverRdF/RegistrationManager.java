package serverRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.User;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.UsersDTO;
import serverRdF.emailRdF.EmailManager;

import java.rmi.RemoteException;
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
    }


    /**
     * @param dbManager il riferimento al manager del db
     * @param emailManager //TODO
     * @return          il singleton della classe.
     */
    public static RegistrationManager createRegistrationManager(DBManager dbManager, EmailManager emailManager) {
        if (registrationManager == null) {
            registrationManager = new RegistrationManager(dbManager,emailManager);
            return registrationManager;
        } else
            return registrationManager;
    }

    /**
     * Registra l'utente nel database. Nel caso non riesca notifica al client l'errore
     *
     * @param form contenente i dati necessari alla registrazione
     * @param c    il riferimento al client
     */
    public void signUp(User form, Client c) {
        boolean bool = dbManager.addUser(form);
        if (!bool) {
            ServerImplementation.serverError(c);
        }
        //TODO la parte di invio della email
        String otp = generateOTP();
        String obj = "Conferma della registrazione a Ruota della Fortuna";
        String text = "Prego inserire il codice: " + otp + " per ultimare la registrazione. Il codice deve essere inserito entro 10 minuti pena l'annullamento della registrazione";
        emailManager.sendEmail(form.getEmail(), obj, text);
    }

    private String generateOTP(){
        Random rd = new Random();
        String res = "";
        for(int i=0; i<6; i++){
            res += rd.nextInt(10);
        }
        return res;
    }

    /**
     * @param email L'indirizzo email da controllare
     * @return <code>true</code> se l'indirizzo email non e' stato gia' utilizzato, <code>false</code> altrimenti
     */
    public boolean checkEmail(String email) {
        UsersDTO user = dbManager.getUserByEmail(email);
        if (user != null) {
            return false;
        } else return true;
    }

    /**
     * @param nickname il nickname da controllare
     * @return <code>true</code> se il nickname non e' stato gia' utilizzato, <code>false</code> altrimenti
     */
    public boolean checkNickname(String nickname) {
        UsersDTO user = dbManager.getUserByNickname(nickname);
        if (user != null) {
            return false;
        } else return true;
    }

    private class WaitingThread extends Thread{
        private Client client;

        public WaitingThread(Client c){
            client = c;
        }

        public void run(){
            int tenMininSec = 600000;
            try{
                sleep(tenMininSec);
            }catch(InterruptedException e){
                try{
                    client.notifyRegistrationConfirmed();
                }catch(RemoteException exc){
                    ServerImplementation.serverError(client);
                }
            }
        }
    }
}

