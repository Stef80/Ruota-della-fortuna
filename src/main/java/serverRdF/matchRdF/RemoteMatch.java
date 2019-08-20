package serverRdF.matchRdF;

import rdFUtil.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteMatch extends Remote {

    /**
     * Metodo utilizzato dal client per girarare la ruota
     *
     * @return Il risultato ottenuto o <code>0</code> se non e' possibile chiamare una consonante (ad esempio ottenendo "PASSA")
     * @throws RemoteException
     */
    public int wheelSpin() throws RemoteException;

    /**
     * Metodo utilizzato dal client per inviare una consonante
     *
     * @param letter la consonante scelta
     * @param amount la quantita' di punti che spettano per ogni lettera rivelata
     * @throws RemoteException
     */
    public void giveConsonant(char letter, int amount) throws RemoteException;

    /**
     * Metodo utilizzato dal client per inviare una vocale
     *
     * @param letter la vocale scelta
     * @throws RemoteException
     */
    public void giveVocal(char letter) throws RemoteException;

    /**
     * Metodo per utilizzare un jolly
     *
     * @throws RemoteException
     */
    public void jolly() throws RemoteException;

    public void nextTurn() throws RemoteException;

    /**
     * Permette al client di dichiarare l'intenzione di dare la soluzione e far partire il timer da 10 secondi.
     *
     * @throws RemoteException
     */
    public void askForSolution() throws RemoteException;

    public void giveSolution(String solution) throws RemoteException;

    public void startMatch() throws RemoteException;

    public void endManche(Player winner) throws RemoteException;

    public void endMatch(boolean isThereAWinner) throws RemoteException;

    public boolean addPlayer(Client c) throws RemoteException;

    public void addObserver(Client c) throws RemoteException;

    public void leaveMatchAsPlayer(Client c) throws RemoteException;

    public void leaveMatchAsObserver(Client c) throws RemoteException;

    public void askNotify(Client c) throws RemoteException;

    public String getMatchId() throws RemoteException;

    //TODO REMINDER: alcuni di questi metodi verranno tolti dall'interfaccia in quanto non utilizzati direttamente dal client
}
