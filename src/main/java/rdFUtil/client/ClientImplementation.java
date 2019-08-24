package rdFUtil.client;

import rdFUtil.view.GamePlayerController;

import java.rmi.RemoteException;

public class ClientImplementation implements Client {

    private GamePlayerController game;
    private String id;
    private String nickname;
    private String name;
    private String surname;
    private String email;

    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    @Override
    public String getId() throws RemoteException {
        return null;
    }

    @Override
    public void setNickname(String nickname) throws RemoteException {
        this.nickname = nickname;
    }

    @Override
    public void setId(String id) throws RemoteException {
        this.id = id;
    }

    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) throws RemoteException {
        this.surname = surname;
    }

    @Override
    public void setEmail(String email) throws RemoteException {
        this.email = email;
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
    public void notifyPlayerStats(int position, String name, int partialPoints, int points, int numJolly) throws RemoteException {
    }

    @Override
    public void updatePhrase(boolean[] phrase) throws RemoteException {

    }

    @Override
    public void updatePhrase(String letter) throws RemoteException {
    }

    @Override
    public void notifyTimeOut() throws RemoteException {
    }

    @Override
    public void notifyWheelResult(String risultato) throws RemoteException {
        game.wheelResult(risultato);
    }

    @Override
    public void askForJolly() throws RemoteException {
    }

    @Override
    public void notifyPlayerError(String name) throws RemoteException {
    }

    public void setGameController(GamePlayerController game){
        this.game = game;
    }

    @Override
    public void notifyTryForSolution(String name) throws RemoteException {
        game.callSolutionNotify(name);
    }

    @Override
    public void notifyTryVocal(String name) throws RemoteException {
        game.vocalCallNotify(name);
    }

    @Override
    public void notifyJollyUsed(String name) throws RemoteException {
        game.jollyNotify(name);
    }

    @Override
    public void notifyLetterCall(String name, String letter) throws RemoteException {
        game.callLetterNotify(name, letter);
    }
}
