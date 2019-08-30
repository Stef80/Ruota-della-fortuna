package rdFUtil.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rdFUtil.ApplicationCloser;
import rdFUtil.Notification;
import rdFUtil.client.Client;
import serverRdF.matchRdF.RemoteMatch;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Il controller della finestra di gioco che visualizza mosse, turni e notifiche di gioco a tutti i giocatori e agli osservatori
 */

public class GamePlayerController implements Initializable {
    @FXML
    private Button jollyButton;
    @FXML
    private Button vowelButton;
    @FXML
    private Button spinButton;
    @FXML
    private Button solutionButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField solutionTextField;
    @FXML
    private TextField letterTextField;
    @FXML
    private Label wheelResultLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Label player3Label;
    @FXML
    private Label partial1Label;
    @FXML
    private Label partial2Label;
    @FXML
    private Label partial3Label;
    @FXML
    private Label total1Label;
    @FXML
    private Label total2Label;
    @FXML
    private Label total3Label;
    @FXML
    private Label jolly1Label;
    @FXML
    private Label jolly2Label;
    @FXML
    private Label jolly3Label;
    @FXML
    private VBox player1Box;
    @FXML
    private VBox player2Box;
    @FXML
    private VBox player3Box;
    @FXML
    private GridPane phraseGridPane;
    @FXML
    private Label phraseThemeLabel;
    private static boolean isObserver;
    private int wheelResult;
    private static RemoteMatch match;
    private Client client;
    private boolean wheelButtonPressed;
    private boolean vowelButtonPressed;

    public GamePlayerController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TabPaneController.creator) {
            TabPaneController.setGameControlle(this);
        } else {
            GameViewController.setGameControllerObserver(this);
            if (!GameViewController.player) {
                hideAll();
            }
        }

        try {
            client.setGame(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        createTableOfPhrase();
        try {
            match.askNotify(client);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        disableAll();
        try {
            if (GameViewController.player)
                match.tryForStartMatch();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Il metodo crea il tabellone dove viene visualizzata la frase misteriosa
     */

    public void createTableOfPhrase() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14; j++) {
                StackPane slotPane = new StackPane();
                Label letterLabel = new Label();
                letterLabel.setVisible(false);
                letterLabel.setStyle("-fx-font-weight: bold;\n" +
                                             "-fx-font-size:36;");
                slotPane.getChildren().add(letterLabel);
                slotPane.setStyle(" -fx-background-color: #eefcf9;\n" +
                        "    -fx-border-color: #08FBE1;\n" +
                        "    -fx-border-radius: 3px;\n" +
                        "    -fx-background-radius: 3px;\n" +
                        "    -fx-border-width: 2px;");
                phraseGridPane.add(slotPane, j, i);
            }

        }
    }

    /**
     * Il metodo aggiorna il tabellone ad ogni lettera chiamata e rende visibile tutte le occorrenze di tale lettera
     *
     * @param letter la lettera scelta
     */

    public void updatePhrase(String letter) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                StackPane node;
                Label label;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 14; j++) {
                        node = (StackPane) getNodeByRowColumnIndex(i, j);
                        label = (Label) node.getChildren().get(0);
                        String let = label.getText();
                        if (let.equals(letter) && !let.equals("")) {
                            label.setVisible(true);
                            node.setStyle(" -fx-background-color: #d6e2e0;\n" +
                                    "    -fx-border-color: #08FBE1;\n" +
                                    "    -fx-border-radius: 3px;\n" +
                                    "    -fx-background-radius: 3px;\n" +
                                    "    -fx-border-width: 2px;");
                        } else if ((let.equals("È") && letter.equals("E")) || (let.equals("Ì") && letter.equals("I")) || (let.equals("À") && letter.equals("A")) || (let.equals("Ò") && letter.equals("O")) || (let.equals("Ù") && letter.equals("U"))) {
                            label.setVisible(true);
                            node.setStyle(" -fx-background-color: #d6e2e0;\n" +
                                    "    -fx-border-color: #08FBE1;\n" +
                                    "    -fx-border-radius: 3px;\n" +
                                    "    -fx-background-radius: 3px;\n" +
                                    "    -fx-border-width: 2px;");
                        }
                    }
                }
            }
        });
    }

    /**
     * Il metodo aggiorna il tabellone degli osservatori entrati a meta' partita con le lettere che sono gia' state richiamate
     *
     * @param phrase l'array di booleani che indica con <code>true</code> se una lettera e' gia' stata chiamata e false altrimenti
     */

    public void updatePhrase(boolean[] phrase) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int column = 0;
                int row = 0;
                int i = 0;
                StackPane node;
                Label label;
                while (i < phrase.length) {
                    node = (StackPane) getNodeByRowColumnIndex(row, column);
                    label = (Label) node.getChildren().get(0);
                    if (!label.isVisible() && !label.getText().equals("")) {
                        if (phrase[i] == true) {
                            label.setVisible(true);
                            node.setStyle(" -fx-background-color: #d6e2e0;\n" +
                                    "    -fx-border-color: #08FBE1;\n" +
                                    "    -fx-border-radius: 3px;\n" +
                                    "    -fx-background-radius: 3px;\n" +
                                    "    -fx-border-width: 2px;");
                            i++;
                            if (column < 13) {
                                column++;
                            } else {
                                column = 0;
                                row++;
                            }
                        } else {
                            i++;
                            if (column < 13) {
                                column++;
                            } else {
                                column = 0;
                                row++;
                            }
                        }
                    } else {
                        if (column < 13) {
                            column++;
                        } else {
                            column = 0;
                            row++;
                        }
                    }
                }
            }
        });
    }

    /**
     * Il metodo aggiunge una nuova frase al tabellone e aggiorna l'etichetta del tema della frase
     *
     * @param theme   Il tema della frase
     * @param phrases La frase
     */

    public void setNewPhrase(String theme, String phrases) {
        final String phrase = phrases.toUpperCase();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                StackPane nod;
                Label labe;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 14; j++) {
                        nod = (StackPane) getNodeByRowColumnIndex(i, j);
                        labe = (Label) nod.getChildren().get(0);
                        labe.setText("");
                        labe.setVisible(false);
                        nod.setStyle(" -fx-background-color: #eefcf9;\n" +
                                "    -fx-border-color: #08FBE1;\n" +
                                "    -fx-border-radius: 3px;\n" +
                                "    -fx-background-radius: 3px;\n" +
                                "    -fx-border-width: 2px;");
                    }
                }
                phraseThemeLabel.setText(theme);
                int column = 0;
                int row = 0;
                char car;
                StackPane node;
                StringTokenizer st = new StringTokenizer(phrase, " ',!?.:;\"/()\\^<>-+*");
                String s = st.nextToken();
                Label label;
                int pointer = 0;
                for (int i = 0; i < phrase.length(); i++) {
                    car = phrase.charAt(i);
                    if (car != ' ' && car != ',' && car != '\'' && car != '.' && car != ':' && car != ';' && car != '?' && car != '!' && car != '"' && car != '/' && car != '\\' && car != '(' && car != ')'
                            && car != '^' && car != '<' && car != '>' && car != '-' && car != '+' && car != '*') {
                        if (pointer == s.length() - 1) {
                            if (st.hasMoreTokens()) {
                                s = st.nextToken();
                                pointer = -1;
                            }
                        } else if (pointer == 0) {
                            if (s.length() > (14 - column)) {
                                column = 0;
                                row++;
                            }
                        }
                        node = (StackPane) getNodeByRowColumnIndex(row, column);
                        label = (Label) node.getChildren().get(0);
                        label.setText("" + car);
                        node.setStyle(" -fx-background-color: #bbbebd;\n" +
                                "    -fx-border-color: #08FBE1;\n" +
                                "    -fx-border-radius: 3px;\n" +
                                "    -fx-background-radius: 3px;\n" +
                                "    -fx-border-width: 2px;");
                        pointer++;
                        if (column < 13) {
                            column++;
                        } else {
                            column = 0;
                            row++;
                        }
                    } else {
                        if (!(car == ' ' && column == 0)) {
                            node = (StackPane) getNodeByRowColumnIndex(row, column);
                            label = (Label) node.getChildren().get(0);
                            label.setText("" + car);
                            if (i < phrase.length() - 1 && phrase.charAt(i + 1) == ' ')
                                i++;
                            label.setVisible(true);
                            if (column < 13) {
                                column++;
                            } else {
                                column = 0;
                                row++;
                            }
                        } else {

                        }
                    }
                }
            }
        });
    }

    /**
     * Il metodo ritorna la casella identificata dalla riga e dalla colonna in cui si trova
     *
     * @param row
     * @param column
     * @return result
     */
    private Node getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = phraseGridPane.getChildren();

        for (Node node : childrens) {
            if (phraseGridPane.getRowIndex(node) == row && phraseGridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    /**
     * Il metodo rende invisibili tutti i pulsanti e disabilita i campi di testo
     */

    public void hideAll() {
        jollyButton.setVisible(false);
        vowelButton.setVisible(false);
        spinButton.setVisible(false);
        solutionButton.setVisible(false);
        solutionTextField.setDisable(true);
        letterTextField.setDisable(true);
    }

    /**
     * Il metodo disabilita tutti i pulsanti e i campi di testo
     */

    public void disableAll() {
        jollyButton.setDisable(true);
        vowelButton.setDisable(true);
        spinButton.setDisable(true);
        solutionButton.setDisable(true);
        solutionTextField.setDisable(true);
        letterTextField.setDisable(true);
    }

    /**
     * Il metodo attiva tutti i bottoni e abilita il campo di testo per l'inserimento delle lettere
     */

    public void activeAll() {
        jollyButton.setDisable(false);
        vowelButton.setDisable(false);
        spinButton.setDisable(false);
        solutionButton.setDisable(false);
        letterTextField.setDisable(false);
    }

    /**
     * Il metodo segnala al server che si vuole provare a dare la soluzione
     *
     * @throws RemoteException In caso di errore di connessione al server
     */
    public void giveSolution() throws RemoteException {
        match.askForSolution();
        solutionTextField.setDisable(false);
    }

    @FXML
    /**
     * Viene inviata al server la soluzione indicata. Se corrisponde alla soluzione effettiva la manche si conclude
     */
    public void confirmSolution() throws RemoteException {
        String solution = solutionTextField.getText();
        solution = solution.trim();
        solution = solution.toUpperCase();
        match.giveSolution(solution);
        solutionTextField.setText("");
    }

    /**
     * Richiede al server un giro della ruota.
     */
    public void wheelSpin() {
        try {
            wheelResult = match.wheelSpin();
            wheelButtonPressed = true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Notifica nella schermata di gioco il valore ottenuto dal giro di ruota
     *
     * @param result il risultato da visualizzare
     */
    public void wheelResult(String result) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                wheelResultLabel.setText(result);
            }
        });
    }

    /**
     * Premendo il tasto 'enter' dopo aver inserito una lettera, verra' comunicato al server che si sta provando a dare una consonante se prima e' stata girata la
     * ruota o una vocale se prima e' stato premuto il tasto 'vocale'
     */
    @FXML
    public void onEnter() throws RemoteException {
        String letter = letterTextField.getText();
        letter = letter.trim();
        letter = letter.toUpperCase();
        if (wheelButtonPressed) {
            match.giveConsonant(letter, wheelResult);
            wheelButtonPressed = false;
        } else if (vowelButtonPressed) {
            match.giveVocal(letter);
            vowelButtonPressed = false;
        }
        letterTextField.setText("");
    }

    /**
     * Metodo per indicare al server che sia ha intenzione di dare una vocale
     *
     * @throws RemoteException In caso di errore di comunicazione con il server
     */
    public void giveVocal() throws RemoteException {
        match.askForVocal();
        vowelButtonPressed = true;
    }

    /**
     * Indica al server che si ha intenzione di utilizzare un jolly
     *
     * @throws RemoteException In caso di errore di comunicazione con il server
     */
    public void giveJolly() throws RemoteException {
        match.jolly();
    }

    /**
     * Modifica la schermata di gioco affinche' venga indicato il turno del giocatore successivo
     *
     * @param nickName il nickname del giocatore attivo
     * @throws RemoteException In caso di errore di comunicazione con il server
     */
    public void setTurn(String nickName) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (nickName.equals(player1Label.getText())) {
                    player1Box.setStyle("-fx-border-color: #FFF404;");
                    player2Box.setStyle("-fx-border-color: #073CA0;");
                    player3Box.setStyle("-fx-border-color: #073CA0;");
                } else if (nickName.equals(player2Label.getText())) {
                    player1Box.setStyle("-fx-border-color: #073CA0;");
                    player2Box.setStyle("-fx-border-color: #FFF404;");
                    player3Box.setStyle("-fx-border-color: #073CA0;");
                } else if (nickName.equals(player3Label.getText())) {
                    player1Box.setStyle("-fx-border-color: #073CA0;");
                    player2Box.setStyle("-fx-border-color: #073CA0;");
                    player3Box.setStyle("-fx-border-color: #FFF404;");
                }
                try {
                    if (!nickName.equals(client.getNickname())) {
                        disableAll();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Attiva i bottoni per effetuare le mosse nel caso sia il proprio turno
     */
    public void yourTurn() {
        activeAll();
    }

    /**
     * Aggiorna le statistiche di uno dei giocatori
     *
     * @param pos      La sua posizione nella schermata (0 a sinistra, 1 al centro e 2 a destra)
     * @param nickname il nickname del giocatore
     * @param partial  il punteggio parziale del giocatore
     * @param total    il punteggio totale del giocatore
     * @param numJolly il numero di jolly accumulati dal giocatore
     */
    public void notifyPlayerStats(int pos, String nickname, int partial, int total, int numJolly) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (pos) {
                    case 0:
                        player1Label.setText(nickname);
                        partial1Label.setText(String.valueOf(partial));
                        total1Label.setText(String.valueOf(total));
                        jolly1Label.setText(String.valueOf(numJolly));
                        break;
                    case 1:
                        player2Label.setText(nickname);
                        partial2Label.setText(String.valueOf(partial));
                        total2Label.setText(String.valueOf(total));
                        jolly2Label.setText(String.valueOf(numJolly));
                        break;
                    case 2:
                        player3Label.setText(nickname);
                        partial3Label.setText(String.valueOf(partial));
                        total3Label.setText(String.valueOf(total));
                        jolly3Label.setText(String.valueOf(numJolly));
                        break;
                }
            }
        });
    }

    /**
     * Notifica che un giocatore ha deciso di chiamare una vocale
     *
     * @param nickname il nickname del giocatore che ha deciso di chiamare la vocale
     */
    public void vocalCallNotify(String nickname) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = nickname + " ha chiamato la vocale";
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    /**
     * Notifica che un giocatore vuole dare la soluzione
     *
     * @param nickname il nickname del giocatore
     */
    public void callSolutionNotify(String nickname) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = nickname + " da la soluzione ";
                Notification.notify("Notifica di partita", message, false);

            }
        });
    }

    /**
     * Notifica che un giocatore ha usato il jolly
     *
     * @param nickname nickname del giocatore
     */
    public void jollyNotify(String nickname) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = nickname + " ha giocato il jolly";
                Notification.notify("Notifica di partita", message, false);
            }
        });

    }

    /**
     * Notifica che un giocatore ha chiamato una lettera
     *
     * @param nickname il nickname del giocatore
     * @param letter   la lettera chiamata
     */
    public void callLetterNotify(String nickname, String letter) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = nickname + " ha scelto la lettera " + letter;
                Notification.notify("Notifica di partita", message, false);

            }
        });
    }


    public void setMatch(RemoteMatch matc) {
        match = matc;
    }

    public void setObserver(boolean observer) {
        isObserver = observer;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Permette al giocatore di abbandonare la partita per poi essere reindirizzato alla finestra principale della piattaforma
     *
     * @throws IOException Nel caso in cui non sia possibile caricare la finestra successiva
     */
    public void exitMatch() throws IOException {
        try {
            if (isObserver) {
                match.leaveMatchAsObserver(client);
            } else {
                match.leaveMatchAsPlayer(client);
            }
        } catch (RemoteException e) {
            System.out.println("Prova");
        } finally {
            match = null;
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("tab_pane.fxml"));
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root);
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            ApplicationCloser.setCloser(primaryStage);
            Stage oldStage = (Stage) exitButton.getScene().getWindow();
            oldStage.close();
        }
    }


    /**
     * Notifica che un giocatore ha abbandonato la partita
     *
     * @param nickname il nickname del giocatore
     */
    public void notifyLeaver(String nickname) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = nickname + "\nha lasciato la partita";
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    /**
     * Notifica che la partita e' stata annullata
     *
     * @param reason il motivo per cui e' stata annullata
     */
    public void notifyMatchAbort(String reason) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("tab_pane.fxml"));
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root);
                    primaryStage.setTitle(FrameTitle.main);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    ApplicationCloser.setCloser(primaryStage);
                    TabPaneController.notifyMatchAbort(reason);
                    Stage oldStage = (Stage) exitButton.getScene().getWindow();
                    oldStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Notifica l'inizio di una partita
     */
    public void notifyMatchStart() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = "Partita iniziata";
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    /**
     * Notifica che l'utente ha vinto la manche
     */
    public void notifyMancheVictory() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", "HAI VINTO LA MANCHE!!!", false);
            }
        });
    }

    /**
     * Notifica la fine della manche
     *
     * @param winner Il nickname del vincitore
     */
    public void notifyMancheResult(String winner) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = winner + "\nha vinto la manche ";
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    /**
     * Notifica l'inizio di una nuova manche
     *
     * @param numManche il numero della manche che sta per iniziare
     */
    public void notifyNewManche(int numManche) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = "la manche numero " + numManche + "\nsta per cominciare";
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    /**
     * Notifica che e' il turno dell'utente
     */
    public void notifyYourTurn() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", "è il tuo turno", false);
            }
        });
        yourTurn();

    }

    /**
     * Notifica la fine della partita e fa tornare l'utente alla schermata principale
     *
     * @param winner il nickname del vincitore
     */
    public void notifyEndMatch(String winner) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = winner + "\nha vinto la partita ";
                match = null;
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("tab_pane.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root);
                primaryStage.setTitle(FrameTitle.main);
                primaryStage.setScene(scene);
                primaryStage.show();
                ApplicationCloser.setCloser(primaryStage);
                TabPaneController.notifyMatchEnd(message);
                Stage oldStage = (Stage) exitButton.getScene().getWindow();
                oldStage.close();
            }
        });
    }

    /**
     * Notifica all'utente che ha vinto la partita e lo fa tornare alla schermata principale
     */
    public void notifyMatchWin() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                match = null;
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("tab_pane.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                Scene scene = new Scene(root);
                primaryStage.setTitle(FrameTitle.main);
                primaryStage.setScene(scene);
                primaryStage.show();
                ApplicationCloser.setCloser(primaryStage);
                TabPaneController.notifyMatchWin();
                Stage oldStage = (Stage) exitButton.getScene().getWindow();
                oldStage.close();
            }
        });
    }

    /**
     * Notifica che il tempo a disposizione di un giocatore e' scaduto
     */
    public void notifyTimeOut() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", "Tempo scaduto", false);

            }
        });
    }

    /**
     * Chiede all'utente se ha intezione di usare un jolly in seguito di un errore
     */
    public void askForJolly() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", "Hai fatto un errore\nVuoi usare il jolly?", false);
            }
        });
    }

    /**
     * Notifica che un giocatore ha commesso un errore
     *
     * @param name il nickname del giocatore
     */
    public void notifyPlayerError(String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = name + "\nha commesso un errore";
                Notification.notify("Notifica di partita", message, false);
            }
        });
    }

    public void notifyNoMoreConsonant() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.notify("Notifica di partita", "sono state chiamate tutte le consonanti", false);
            }
        });
    }

    public void updateTimer(int time) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timerLabel.setText("" + time);
            }
        });
    }
}
