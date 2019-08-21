package rdFUtil.client;

import java.rmi.RemoteException;

public class ClientImplementation implements Client {
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

    @Override
    public void notifyMatchAbort(String reason) throws RemoteException {
    }

    @Override
    public void notifyMatchStart() throws RemoteException {
    }

    @Override
    public void notifyMancheVictory() throws RemoteException {
    }

    @Override
    public void notifyMancheResult(String winner) throws RemoteException {
    }

    @Override
    public void notifyNewManche(int numManche) throws RemoteException {
    }

    @Override
    public void setNewPhrase(String theme, String phrase) throws RemoteException {
    }

    @Override
    public void notifyNewTurn(String player) throws RemoteException {
    }

    @Override
    public void notifyYourTurn() throws RemoteException {
    }

    @Override
    public void notifyEndMatch(String winner) throws RemoteException {
    }

    @Override
    public void notifyMatchWin() throws RemoteException {
    }

    @Override
    public void notifyPlayerStats(int position, String name, int partialPoints, int points) throws RemoteException {
    }

    @Override
    public void updatePhrase(boolean[] phrase) throws RemoteException {
    }

    @Override
    public void updatePhrase(char letter, int amount) throws RemoteException {
    }

    @Override
    public void notifyTimeOut() throws RemoteException {
    }

    @Override
    public void notifyWheelResult(String risultato) throws RemoteException {
    }

    @Override
    public void askForJolly() throws RemoteException {
    }

    @Override
    public void notifyPlayerError(String name) throws RemoteException {
    }
}
