package rdFUtil.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.MatchData;
import rdFUtil.client.Client;
import rdFUtil.logging.CryptPassword;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Controller del menu principale di AdminRdF e PlayerRdF. Permette di visualizzare le partite, crearene di nuove, visualizzare le statistiche della piattaforma
 * e gestire le informazioni del proprio profilo. Se si accede come admin e' anche possibile aggiungere nuove frasi.
 */
public class TabPaneController implements Initializable {

    @FXML
    private ListView<MatchData> gameList;
    @FXML
    private Button createMatchButton;
    @FXML
	private Label numberManchesPlayedLabel;
	@FXML
	private Label numberManchesObservedLabel;
	@FXML
	private Label numberMatchesplayedLabel;
	@FXML
	private Label numberMatchesObservedLabel;
	@FXML
	private Label numberOfMatchesWonLabel;
	@FXML
	private Label numberOfManchesWonLabel;
	@FXML
	private Label averagePointsWonLabel;
	@FXML
	private Label averageTurnLostPerMatchLabel;
	@FXML
	private Label averageTurnLostPerMancheLabel;
	@FXML
	private Label averageLostAllPerMatchLabel;
	@FXML
	private Label averageLostAllPerMancheLabel;
	//statistiche globali
	@FXML
	private Label bestPointsWonMancheLabel;
	@FXML
	private Label bestPointsWonMatchLabel;
	@FXML
	private Label mostManchePlayedLabel;
	@FXML
	private Label averagePointPerMancheLabel;
	@FXML
	private Label mostTimeLostTurnLabel;
	@FXML
	private Label mostTimeLostAllLabel;
	@FXML
	private Label nickBestCallLabel;
	@FXML
	private Label phraseAsociatedLabel;
	@FXML
	private Label letterCalledLabel;
	@FXML
	private Label averageMovesTillSolutionLabel;
	@FXML
	private Label nicknameLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private Label surnameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private TextField nicknameTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button refreshButton;
	@FXML
	private TextField filePhraseTextField;
	@FXML
	private Tab phraseAdder;
	@FXML
	private javafx.scene.control.TabPane tabs;


    private ObservableList<MatchData> gameObservableList = FXCollections.observableArrayList();
    private static Client client;
    private static Server server;
    private static RemoteMatch match;
    private static MatchData matchData;
    private boolean isAdmin;


    public TabPaneController(){
	}

	/**
	 * Metodo utilizzato per la creazione di un nuovo match. Carichera' anche la schermata di gioco
	 *
	 * @param actionEvent
	 * @throws RemoteException in caso di errore di comunicazione con il server
	 */
    public void addMatch(ActionEvent actionEvent) throws RemoteException {
    	match = server.createMatch(client);
        GamePlayerController.setMatch(match);
        GamePlayerController.setObserver(false);

        FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("game_player_pane.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GamePlayerController game = loader.getController();
        game.createTableOfPhrase();
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        Stage oldStage = (Stage) createMatchButton.getScene().getWindow();
        oldStage.hide();

    }

    @Override
	/**
	 * Inizializza il controller caricando automaticamente le partite disponibili, le statistiche di utilizzo della piattaforma e le informazioni del proprio profilo
	 */
    public void initialize(URL location, ResourceBundle resources) {
    	Controller.setArgs(this);

    	gameList.setItems(gameObservableList);
		ArrayList<MatchData> list = new ArrayList<>();
		try {
			list = server.visualizeMatch(client);
			gameObservableList.addAll(list);
			gameList.setItems(gameObservableList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (MatchData matchData: list){
			gameList.setCellFactory(e -> new GameViewController(server, client, matchData));
		}
		disableTab();
		try {
			setUserStat();
            setGlobalStats(); //todo da provare con database attivo
		} catch (RemoteException e) {
			Notifications notification = Notifications.create()
												 .title("Notifica erorre")
												 .text("statistiche non caricate")
												 .hideAfter(Duration.seconds(2))
												 .position(Pos.BASELINE_CENTER);
			notification.showError();
		}

		try {
			nicknameLabel.setText(client.getNickname());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		nameLabel.setText(client.getName());
		surnameLabel.setText(client.getSurname());
		emailLabel.setText(client.getEmail());
	}

    private void disableTab(){
		if(!this.isAdmin){
			tabs.getTabs().remove(phraseAdder);
		}else {
			phraseAdder.setDisable(false);
		}
	}
    @FXML
	/**
	 * Carica le statistiche relative all'utente loggato.
	 */
	public void setUserStat() throws RemoteException {
    	String userStat = server.checkPerPlayer(client.getNickname());
//		String userStat = "";
    	if(!userStat.equals("")) {

			StringTokenizer stasts = new StringTokenizer(userStat, " ");
			numberManchesPlayedLabel.setText(stasts.nextToken());
			numberMatchesplayedLabel.setText(stasts.nextToken());
			numberManchesObservedLabel.setText(stasts.nextToken());
			numberMatchesObservedLabel.setText(stasts.nextToken());
			numberOfManchesWonLabel.setText(stasts.nextToken());
			numberOfMatchesWonLabel.setText(stasts.nextToken());
			averagePointsWonLabel.setText(stasts.nextToken());
			averageTurnLostPerMancheLabel.setText(stasts.nextToken());
			averageTurnLostPerMatchLabel.setText(stasts.nextToken());
			averageLostAllPerMancheLabel.setText(stasts.nextToken());
			averageLostAllPerMatchLabel.setText(stasts.nextToken());
		}else{
			numberManchesPlayedLabel.setText("0");
			numberMatchesplayedLabel.setText("0");
			numberManchesObservedLabel.setText("0");
			numberMatchesObservedLabel.setText("0");
			numberOfManchesWonLabel.setText("0");
			numberOfMatchesWonLabel.setText("0");;
			averagePointsWonLabel.setText("0");
			averageTurnLostPerMancheLabel.setText("0");
			averageTurnLostPerMatchLabel.setText("0");
			averageLostAllPerMancheLabel.setText("0");
			averageLostAllPerMatchLabel.setText("0");
		}

	}


   @FXML
   /**
	* Carica i record di utilizzo della piattaforma
	*/
   public void setGlobalStats() throws RemoteException {
    	String recordStatStr = server.checkRecordPlayer();

    	int avSolManches = server.averageManches();

	    String strBestMove = server.bestMove();
//           String recordStatStr = " ";
//           int avSolManches = 1;
//           String strBestMove = " ";
	    StringTokenizer bestMove = new StringTokenizer(strBestMove, " ");
    	StringTokenizer recordStat = new StringTokenizer(recordStatStr," ");

    	bestPointsWonMancheLabel.setText(recordStat.nextToken());
    	bestPointsWonMatchLabel.setText(recordStat.nextToken());
    	mostManchePlayedLabel.setText(recordStat.nextToken());
    	averagePointPerMancheLabel.setText(recordStat.nextToken());
    	mostTimeLostTurnLabel.setText(recordStat.nextToken());
    	mostTimeLostAllLabel.setText(recordStat.nextToken());

    	nickBestCallLabel.setText(bestMove.nextToken());
    	letterCalledLabel.setText(bestMove.nextToken());
    	phraseAsociatedLabel.setText(bestMove.nextToken());

    	averageMovesTillSolutionLabel.setText(String.valueOf(avSolManches));
   }

	/**
	 * Notifica che la partita alla quale si ha provato a partecipare come giocatore e' piena
	 */
	public void notifyTooManyPlayers() {
		Notifications notification = Notifications.create()
											 .title("Giocatori")
											 .text("Troppi giocatori")
											 .hideAfter(Duration.seconds(2))
											 .position(Pos.BASELINE_CENTER);
		notification.showInformation();

	}

	/**
	 * Ricarica la lista delle partite disponibili aggiornata
	 */
	public void refresh(){
		gameList.setItems(gameObservableList);
		ArrayList<MatchData> list = new ArrayList<>();
		try {
			list = server.visualizeMatch(client);
			gameObservableList.addAll(list);
			gameList.setItems(gameObservableList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (MatchData matchData: list){
			gameList.setCellFactory(e -> new GameViewController(server, client, matchData));
		}
	}

	/**
	 * Legge il file .csv inserito per l'aggiunta di nuove frasi e lo invia al server
	 */
	public void enterFilePhrase(){
    	String phrases = filePhraseTextField.getText();
    	String phrasesTrim = phrases.trim();
		File filePhrases = new File(phrasesTrim);
		try {
			boolean bool = server.addPhrases(filePhrases);
			if(bool){
				Notifications notification = Notifications.create()
						.title("Successo")
						.text("Le frasi sono state aggiunte con successo")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showInformation();
			}else{
				Notifications notification = Notifications.create()
						.title("Notifica Errore")
						.text("Non e' stato possibile aggiungere le nuove frasi\n Riprova")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showError();
			}
		} catch (RemoteException e) {
			Notifications notification = Notifications.create()
												 .title("Notifica Errore")
												 .text("Server offline")
												 .hideAfter(Duration.seconds(2))
												 .position(Pos.BASELINE_CENTER);
			notification.showError();
		}

	}

	/**
	 * Permette di cambiare il proprio nome.
	 *
	 * @throws RemoteException In caso di errore di connessione al server
	 */
	public void changeName() throws RemoteException{
    	String name = nameTextField.getText();
    	if(!name.equals("")) {
			boolean bool = server.changeName(name, client);
			if (bool) {
				nameLabel.setText(name);
				Notifications notification = Notifications.create()
						.title("Successo")
						.text("Il nome e' stato modificato con successo")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showInformation();
			} else {
				Notifications notification = Notifications.create()
						.title("Notifica Errore")
						.text("Non e' stato possibile modificare il nome")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showError();
			}
		}else{

		}
	}

	/**
	 * Permette di cambiare il proprio cognome.
	 *
	 * @throws RemoteException In caso di un errore di connesione al server
	 */
	public void changeSurname() throws RemoteException{
		String surname = surnameTextField.getText();
		if(surname.equals("")) {
			boolean bool = server.changeSurname(surname, client);
			if (bool) {
				surnameLabel.setText(surname);
				Notifications notification = Notifications.create()
						.title("Successo")
						.text("Il cognome e' stato modificato con successo")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showInformation();
			} else {
				Notifications notification = Notifications.create()
						.title("Notifica Errore")
						.text("Non e' stato possibile modificare il cognome")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showError();
			}
		}else{

		}
	}

	/**
	 * Permette di cambiare il proprio nickname.
	 *
	 * @throws RemoteException In caso di errore di connessione al server
	 */
	public void changeNickname() throws RemoteException{
		String nickname = nicknameTextField.getText();
		if(nickname.equals("")) {
			boolean bool = server.changeNickname(nickname, client);
			if (bool) {
				nicknameLabel.setText(nickname);
				Notifications notification = Notifications.create()
						.title("Successo")
						.text("Il nickname e' stato modificato con successo")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showInformation();
			} else {
				Notifications notification = Notifications.create()
						.title("Notifica Errore")
						.text("Non e' stato possibile modificare il nickname oppure e' gia' in uso")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showError();
			}
		}else{

		}
	}

	/**
	 * Permette di modificare la propria password
	 *
	 * @throws RemoteException In caso di errore di connesione al server
	 */
	public void changePassword() throws RemoteException{
		String password = passwordField.getText();
		if(password.equals("")) {
			password = CryptPassword.encrypt(password);
			boolean bool = server.changePassword(password, client);
			if (bool) {
				Notifications notification = Notifications.create()
						.title("Successo")
						.text("La password e' stata modificata con successo")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showInformation();
			} else {
				Notifications notification = Notifications.create()
						.title("Notifica Errore")
						.text("Non e' stato possibile modificare la password")
						.hideAfter(Duration.seconds(2))
						.position(Pos.BASELINE_CENTER);
				notification.showError();
			}
		}else{

		}
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
}



