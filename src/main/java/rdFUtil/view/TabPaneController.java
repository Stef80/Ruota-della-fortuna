package rdFUtil.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rdFUtil.MatchData;
import rdFUtil.Notification;
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
    @FXML
    private Tab userStatisticsTab;
    @FXML
    private Tab userStatisticsTabAdmin;


    private ObservableList<MatchData> gameObservableList = FXCollections.observableArrayList();
    private static Client client;
    private static Server server;
    private static RemoteMatch match;
    private static MatchData matchData;
    private boolean isAdmin;
    public static boolean creator = true;


    public TabPaneController() {
    }

    /**
     * Metodo utilizzato per la creazione di un nuovo match. Carichera' anche la schermata di gioco
     *
     * @param actionEvent //todo
     * @throws RemoteException in caso di errore di comunicazione con il server
     */
    public void addMatch(ActionEvent actionEvent) throws RemoteException {
        try {
            match = server.createMatch(client);
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("game_player_pane.fxml"));
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setOnCloseRequest((WindowEvent event1) -> {
                    try {
                        match.leaveMatchAsPlayer(client);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                });
                Stage oldStage = (Stage) createMatchButton.getScene().getWindow();
                oldStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            client.notifyServerError();
        }

    }

    @Override
    /**
     * Inizializza il controller caricando automaticamente le partite disponibili, le statistiche di utilizzo della piattaforma e le informazioni del proprio profilo
     */
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setArgs(this);
        creator = true;

        if (isAdmin) {
            createMatchButton.setVisible(false);
        }

        gameList.setItems(gameObservableList);
        ArrayList<MatchData> list = new ArrayList<>();
        try {
            list = server.visualizeMatch(client);
            gameObservableList.addAll(list);
            gameList.setItems(gameObservableList);
        } catch (RemoteException e) {
            Notification.notify("Errore", "Server offline", true);
        }
        for (MatchData matchData : list) {
            gameList.setCellFactory(e -> new GameViewController(server, client, matchData));
        }
        disableTab();
        try {
            setUserStat();
            setGlobalStats();
        } catch (RemoteException e) {
            Notification.notify("Errore", "Statistiche non caricate", true);
        }

        try {
            nicknameLabel.setText(client.getNickname());
            nameLabel.setText(client.getName());
            surnameLabel.setText(client.getSurname());
            emailLabel.setText(client.getEmail());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void disableTab() {
        if (!this.isAdmin) {
            tabs.getTabs().remove(phraseAdder);
            tabs.getTabs().remove(userStatisticsTabAdmin);
        } else {
            tabs.getTabs().remove(userStatisticsTab);
        }
    }

    @FXML
    /**
     * Carica le statistiche relative all'utente loggato.
     */
    public void setUserStat() throws RemoteException {
        String userStat = server.checkPerPlayer(client.getNickname());
//		String userStat = "";
        if (!(userStat == null)) {

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
        } else {
            numberManchesPlayedLabel.setText("0");
            numberMatchesplayedLabel.setText("0");
            numberManchesObservedLabel.setText("0");
            numberMatchesObservedLabel.setText("0");
            numberOfManchesWonLabel.setText("0");
            numberOfMatchesWonLabel.setText("0");
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
        try {
            String recordStatStr = server.checkRecordPlayer();

            int avSolManches = server.averageManches();

            String strBestMove = server.bestMove();
//           String recordStatStr = " ";
//           int avSolManches = 1;
//           String strBestMove = " ";
            StringTokenizer bestMove = new StringTokenizer(strBestMove, " ");
            StringTokenizer recordStat = new StringTokenizer(recordStatStr, " ");

            bestPointsWonMancheLabel.setText(recordStat.nextToken());
            bestPointsWonMatchLabel.setText(recordStat.nextToken());
            mostManchePlayedLabel.setText(recordStat.nextToken());
            averagePointPerMancheLabel.setText(recordStat.nextToken());
            mostTimeLostTurnLabel.setText(recordStat.nextToken());
            mostTimeLostAllLabel.setText(recordStat.nextToken());

            nickBestCallLabel.setText(bestMove.nextToken());
            letterCalledLabel.setText(bestMove.nextToken());
            String phrase = "";
            while (bestMove.hasMoreElements()) {
                phrase += bestMove.nextToken() + " ";
            }
            phraseAsociatedLabel.setText(phrase);

            averageMovesTillSolutionLabel.setText(String.valueOf(avSolManches));
        } catch (RemoteException e) {
            Notification.notify("Errore", "Server offline", true);
        }
    }

    /**
     * Notifica che la partita alla quale si ha provato a partecipare come giocatore e' piena
     */
    public void notifyTooManyPlayers() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Giocatori", "Troppi giocatori", true);
            }
        });
    }

    /**
     * Ricarica la lista delle partite disponibili aggiornata
     */
    public void refresh() {
        gameObservableList = FXCollections.observableArrayList();
        gameList.setItems(gameObservableList);
        ArrayList<MatchData> list = new ArrayList<>();
        try {
            list = server.visualizeMatch(client);
            gameObservableList.addAll(list);
            gameList.setItems(gameObservableList);
        } catch (RemoteException e) {
            Notification.notify("Errore", "Non è stato possibile aggiornare la lista dei match\n Riprova", true);
        }
        for (MatchData matchData : list) {
            gameList.setCellFactory(e -> new GameViewController(server, client, matchData));
        }
    }

    /**
     * Legge il file .csv inserito per l'aggiunta di nuove frasi e lo invia al server
     */
    public void enterFilePhrase() {
        String phrases = filePhraseTextField.getText();
        String phrasesTrim = phrases.trim();
        File filePhrases = new File(phrasesTrim);
        try {
            boolean bool = server.addPhrases(filePhrases);
            if (bool) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Notification.notify("Successo", "Le frasi sono state aggiunte con successo", false);
                    }
                });

            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Notification.notify("Errore", "Non è stato possibile aggiungere le nuove frasi\n Riprova", true);
                    }
                });
            }
        } catch (RemoteException e) {
            Notification.notify("Errore", "Server offline", true);
        }
    }

    /**
     * Permette di cambiare il proprio nome.
     *
     * @throws RemoteException In caso di errore di connessione al server
     */
    public void changeName() {
        String name = nameTextField.getText();
        if (!name.equals("")) {
            boolean bool = false;
            try {
                bool = server.changeName(name, client);
            } catch (RemoteException e) {
                Notification.notify("Errore", "Server offline", true);
            }
            if (bool) {
                nameLabel.setText(name);
                try {
                    client.setName(name);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Notification.notify("Successo", "Il nome è stato modificato con successo", false);
            } else {
                Notification.notify("Errore", "Non è stato possibile modificare il nome", true);
            }
        }
    }

    /**
     * Permette di cambiare il proprio cognome.
     *
     * @throws RemoteException In caso di un errore di connesione al server
     */
    public void changeSurname() {
        String surname = surnameTextField.getText();
        if (!surname.equals("")) {
            boolean bool = false;
            try {
                bool = server.changeSurname(surname, client);
            } catch (RemoteException e) {
                Notification.notify("Errore", "Server offline", true);
            }
            if (bool) {
                surnameLabel.setText(surname);
                try {
                    client.setSurname(surname);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Notification.notify("Successo", "Il cognome è stato modificato con successo", false);
            } else {
                Notification.notify("Errore", "Non è stato possibile modificare il cognome", true);
            }
        }
    }

    /**
     * Permette di cambiare il proprio nickname.
     *
     * @throws RemoteException In caso di errore di connessione al server
     */
    public void changeNickname() {
        String nickname = nicknameTextField.getText();
        if (!nickname.equals("")) {
            boolean bool = false;
            try {
                bool = server.changeNickname(nickname, client);
            } catch (RemoteException e) {
                Notification.notify("Errore", "Server offline", true);
            }
            if (bool) {
                try {
                    client.setNickname(nickname);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                nicknameLabel.setText(nickname);
                Notification.notify("Successo", "Il nickname è stato modificato con successo", false);
            } else {
                Notification.notify("Errore", "Non è stato possibile modificare il nickname oppure è già in uso", true);
            }
        }
    }

    /**
     * Permette di modificare la propria password
     *
     * @throws RemoteException In caso di errore di connesione al server
     */
    public void changePassword() {
        String password = passwordField.getText();
        if (!password.equals("")) {
            password = CryptPassword.encrypt(password);
            boolean bool = false;
            try {
                bool = server.changePassword(password, client);
            } catch (RemoteException e) {
                Notification.notify("Errore", "Server offline", true);
            }
            if (bool) {
                Notification.notify("Successo", "La password è stata modificata con successo", false);
            } else {
                Notification.notify("Errore", "Non è stato possibile modificare la password", true);
            }
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

    public static void setGameControlle(GamePlayerController gpc) {
        gpc.setClient(client);
        gpc.setMatch(match);
        gpc.setObserver(false);
    }

    public static void notifyMatchAbort(String reason) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", reason, false);
            }
        });
    }

    public static void notifyMatchEnd(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    public static void notifyMatchWin() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", "HAI VINTO!!!", false);
            }
        });
    }
}



