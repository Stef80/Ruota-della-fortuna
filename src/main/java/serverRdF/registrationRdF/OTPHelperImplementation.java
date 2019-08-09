package serverRdF.registrationRdF;

import rdFUtil.client.Client;
import rdFUtil.logging.CryptPassword;
import serverRdF.ServerImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OTPHelperImplementation extends UnicastRemoteObject implements OTPHelper {
    private String otp;
    private WaitingThread thread;

    public OTPHelperImplementation(WaitingThread t, String code) throws RemoteException {
        otp = code;
        thread = t;
    }

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
