package serverRdF.registrationRdF;

import rdFUtil.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OTPHelper extends Remote {

    /**
     * Il client invia il codice per poi ricevere la conferma della registrazione, attraverso {@link WaitingThread}, oppure l'inserimento di un codice errato
     *
     * @param otp codice inviato dal client
     * @param c   riferimento al client
     * @throws RemoteException in caso di errore di connessione al server
     */
    public boolean checkOTP(String otp, Client c) throws RemoteException;
}
