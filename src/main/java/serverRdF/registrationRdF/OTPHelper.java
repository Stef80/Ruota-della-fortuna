package serverRdF.registrationRdF;

import rdFUtil.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OTPHelper extends Remote {

    public void checkOTP(String otp, Client c) throws RemoteException;
}
