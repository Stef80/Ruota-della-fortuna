package rdFUtil.client;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia dell'oggetto remoto utilizzato da PlayerRdF e AdminRdF per ricevere notifiche dal server
 */
public interface Client extends Remote {
    /**
     * Questo metodo permette di ottenere il nickname dell'utente loggato nel client
     *
     * @return il nickname
     * @throws RemoteException
     */
    public String getNickname() throws RemoteException;

    /**
     * Questo metodo permette di ottenere l'id dell'utente loggato nel client
     *
     * @return l'id utente
     * @throws RemoteException
     */
    public String getId() throws RemoteException;

    /**
     * Permette di settare il nickname dell'utente loggato nel client
     *
     * @throws RemoteException
     */
    public void setNickname(String nickname) throws RemoteException;

    /**
     * Permette di settare il nickname dell'utente loggato nel client
     *
     * @throws RemoteException
     */
    public void setId(String id) throws RemoteException;

    public void setName(String name) throws RemoteException;

    public void setSurname(String surname) throws RemoteException;

    public void setEmail(String email) throws RemoteException;

    /**
     * Notifica al client che c'e' stato un problema di connessione al server da parte del client o al database da parte del server
     *
     * @throws RemoteException
     */
    public void notifyServerError() throws RemoteException;

    /**
     * Notifica se e' stato possibile concludere la registrazione
     *
     * @param success <code>true</code> se la registrazione e' avvenuta con successo, <code>false</code> altrimenti
     * @throws RemoteException
     */
    public void notifyRegistrationResult(boolean success) throws RemoteException;


    /**
     * Notifica che il codice inserito per ultimare la registrazione e' errato
     *
     * @throws RemoteException
     */
    public void notifyWrongOTP() throws RemoteException;

    /**
     * Notifica che non è stato possibile accedere alla partita poiche' e' piena
     *
     * @throws RemoteException
     */
    public void notifyTooManyPlayers() throws RemoteException;

    /**
     * Notifica che uno degli altri giocatori ha abbandonato la partita
     *
     * @param nickname il nickname di chi ha abbandonato
     * @throws RemoteException
     */
    public void notifyLeaver(String nickname) throws RemoteException;

    /**
     * Notifica che la partita e' stata annullata
     *
     * @param reason il motivo per cui la partita e' stata annullata
     * @throws RemoteException
     */
    public void notifyMatchAbort(String reason) throws RemoteException;

    /**
     * Notifica l'inizio della partita
     *
     * @throws RemoteException
     */
    public void notifyMatchStart() throws RemoteException;

    /**
     * Notifica che il client è colui che ha vinto la manche
     *
     * @throws RemoteException
     */
    public void notifyMancheVictory() throws RemoteException;

    /**
     * Notifica il client che <code>winner</code> ha vinto la manche
     *
     * @param winner il nickname del vincitore della manche
     * @throws RemoteException
     */
    public void notifyMancheResult(String winner) throws RemoteException;

    /**
     * Notifica al client l'inizio di una nuova manche
     *
     * @param numManche il numero dell manche appena iniziata
     * @throws RemoteException
     */
    public void notifyNewManche(int numManche) throws RemoteException;

    /**
     * Questo metodo si occupa di aggiornare il tabellone con il nuovo tema e la nuova frase
     *
     * @param theme  il tema della nuova frase
     * @param phrase la nuova frase da indovinare
     * @throws RemoteException
     */
    public void setNewPhrase(String theme, String phrase) throws RemoteException;

    /**
     * Notifica ai clients il nome del giocatore attivo
     *
     * @param player Il nickname del giocatore attivo
     * @throws RemoteException
     */
    public void notifyNewTurn(String player) throws RemoteException;

    /**
     * Attiva i bottoni per poter effettuare le mosse
     *
     * @throws RemoteException
     */
    public void notifyYourTurn() throws RemoteException;


    /**
     * Questo metodo notifica la conclusione del match e il nome del vincitore
     *
     * @param winner il nickname del vincitore
     * @throws RemoteException
     */
    public void notifyEndMatch(String winner) throws RemoteException;


    /**
     * Notifica al client che ha vinto la partita
     *
     * @throws RemoteException
     */
    public void notifyMatchWin() throws RemoteException;

    /**
     * Metodo per far visualizzare un giocatore e i suoi punteggi
     *
     * @param position      e' un numero compreso tra <code>0</code> e <code>2</code> (<code>0</code> e' il giocatore piu' a sinistra)
     * @param name          il nickname del giocatore
     * @param partialPoints il punteggio parziale
     * @param points        il punteggio totale
     * @throws RemoteException
     */
    public void notifyPlayerStats(int position, String name, int partialPoints, int points) throws RemoteException;

    /**
     * Metodo per visualizzare solo le lettere che sono state chiamate. Per funzionare e' necessario aver richiamato precedentemente il metodo
     * {@link #setNewPhrase(String, String)} che si occupa di generare il tabellone adatto alla farse
     *
     * @param phrase un array di booleani che indica, senza considerare gli spazi, se un carattere nella frase sia stato rivelato o meno.
     *               Nel primo caso avra' valore <code>true</code>, nel secondo <code>false</code>.
     * @throws RemoteException
     */
    public void updatePhrase(boolean[] phrase) throws RemoteException;

    /**
     * Una versione piu' semplice di {@link #updatePhrase(boolean[])} per aggiornare il tabellone senza dover utilizzare
     * l'array dell'intera frase e in piu' notifica quanti punti ha fatto guadagnare
     *
     * @param letter la lettera chiamata
     * @param amount il punteggio guadagnato per ogni lettera rivelata
     * @throws RemoteException
     */
    public void updatePhrase(String letter, int amount) throws RemoteException;

    /**
     * Notifica al client che il tempo per concludere la mossa e' finito
     *
     * @throws RemoteException
     */
    public void notifyTimeOut() throws RemoteException;

    /**
     * Questo metodo visualizza il risulato ottenuto da un giro di ruota
     *
     * @param risultato il risultato ottenuto
     * @throws RemoteException
     */
    public void notifyWheelResult(String risultato) throws RemoteException;

    /**
     * Chiede al giocatore che ha effettuato un errore se vuole usarlo
     *
     * @throws RemoteException
     */
    public void askForJolly() throws RemoteException;

    /**
     * Notifica che il giocatore attivo ha commesso un errore, come far scadere il timer o eseguire una mossa illegale
     *
     * @param name il nickname del giocatore
     * @throws RemoteException
     */
    public void notifyPlayerError(String name) throws RemoteException;

    public void notifyTryForSolution(String name) throws RemoteException;

    public void notifyTryVocal(String name) throws RemoteException;

    public void notifyJollyUsed(String name) throws RemoteException;

    public void notifyLetterCall(String name, String letter) throws RemoteException;
}
