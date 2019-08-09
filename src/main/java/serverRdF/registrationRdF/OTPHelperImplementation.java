package serverRdF.registrationRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.CryptPassword;
import serverRdF.ServerImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Oggetto remoto utilizzato dal client per inviare l'OTP ricevuta via email. Se il codice è esatto permette a {@link WaitingThread} di completare la registrazione.
 */
public class OTPHelperImplementation extends UnicastRemoteObject implements OTPHelper {
    private String otp;
    private WaitingThread thread;

    public OTPHelperImplementation(WaitingThread t, String code) throws RemoteException {
        otp = code;
        thread = t;
    }


    /**
     * Il client invia il codice per poi ricevere la conferma della registrazione, attraverso {@link WaitingThread}, oppure l'inserimento di un codice errato
     *
     * @param otp codice inviato dal client
     * @param c riferimento al client
     * @throws RemoteException in caso di errore di connessione al server
     */
    public void checkOTP(String otp, Client c) throws RemoteException{
        String cryptedOTP = CryptPassword.encrypt(otp);
        if(cryptedOTP.equals(this.otp)){
            thread.interrupt();
        }else{
            try{
                c.notifyWrongOTP();
            }catch(RemoteException e){
                ServerImplementation.serverError(c);
            }
        }
    }
}
