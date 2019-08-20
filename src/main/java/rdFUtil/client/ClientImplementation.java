package rdFUtil.client;

import java.rmi.RemoteException;

public class ClientImplementation implements Client{
	@Override
	public String getNickname() throws RemoteException {
		return null;
	}

	@Override
	public String getId() throws RemoteException {
		return null;
	}

	@Override
	public void setNickname() throws RemoteException {

	}

	@Override
	public void setId() throws RemoteException {

	}

	@Override
	public void notifyServerError() throws RemoteException {

	}

	@Override
	public void notifyRegistrationResult(boolean success) throws RemoteException {

	}

	@Override
	public void notifyWrongOTP() throws RemoteException {

	}

	@Override
	public void notifyTooManyPlayers() throws RemoteException {

	}

	@Override
	public void notifyLeaver(String nickname) throws RemoteException {

	}
}
