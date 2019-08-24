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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class TabPane implements Initializable {

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


    private ObservableList<MatchData> gameObservableList = FXCollections.observableArrayList();
    private Client client;
    private Server server;
    private RemoteMatch match;
    private MatchData matchData;
    private boolean isAdmin;


    public TabPane(){
	}

    public void addMatch(ActionEvent actionEvent) throws RemoteException, NotBoundException {
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
    public void initialize(URL location, ResourceBundle resources) {
    	Controller.setArgs(this);

    	gameList.setItems(gameObservableList);
//		ArrayList<MatchData> list = new ArrayList<>();
//		try {
//			list = server.visualizeMatch(client);
//			gameObservableList.addAll(list);
//			gameList.setItems(gameObservableList);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		for (MatchData matchData: list){
//			gameList.setCellFactory(e -> new GameView(server, client, matchData));
//		}
		disableTab();
		}

    public void disableTab(){
		if(!this.isAdmin){
			phraseAdder.setDisable(true);
		}else {
			phraseAdder.setDisable(false);
		}
	}
    @FXML
	public void setUserStat() throws RemoteException {
    //	String userStat = server.checkPerPlayer(client.getNickname());
		String userStat = "";
    	if(!userStat.equals(null)) {

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

//todo chiedere quale dei due prima
// numero medio di volte che ha dovuto cedere il turno di gioco per manche,
//	numero medio di volte che ha dovuto cedere il turno di gioco per manche,
   @FXML
   public void setGlobalStats() throws RemoteException {
    //	String recordStatStr = server.checkRecordPlayer();

    //	int avSolManches = server.averageManches();

	//    String strBestMove = server.bestMove();
           String recordStatStr = " ";
           int avSolManches = 1;
           String strBestMove = " ";
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

	public void notifyTooManyPlayers() {
		Notifications notification = Notifications.create()
											 .title("Giocatori")
											 .text("Troppi giocatori")
											 .hideAfter(Duration.seconds(2))
											 .position(Pos.BASELINE_CENTER);
		notification.showInformation();

	}

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
			gameList.setCellFactory(e -> new GameView(server, client, matchData));
		}
	}

	public void enterFilePhrase(){
    	String phrases = filePhraseTextField.getText();
    	String phrasesTrim = phrases.trim();
		File filePhrases = new File(phrasesTrim);
		try {
			server.addPhrases(filePhrases);
		} catch (RemoteException e) {
			Notifications notification = Notifications.create()
												 .title("Notifica Errore")
												 .text("File non Caricato")
												 .hideAfter(Duration.seconds(2))
												 .position(Pos.BASELINE_CENTER);
			notification.showError();
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



