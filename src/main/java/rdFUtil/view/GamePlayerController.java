package rdFUtil.view;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

public class GamePlayerController{
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
    private Button confirmSolutionButton;
    @FXML
    private TextField solutionTextField;
    @FXML
    private TextField letterTextfield;
    @FXML
    private Label resultLabel;
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
    private GridPane phraseGridpane;
    @FXML
    private Label phraseThemeLabel;
    private static boolean isObserver;
    private Timeline timeline;
    private int timeSeconds;
    private int wheelResult;
    private static RemoteMatch match;
    private Client client;

    public GamePlayerController(){

    }

    public GamePlayerController( Client client){
        this.client = client;
    }

    public void createTableOfPhrase(){

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14 ; j++) {
                StackPane slotPane = new StackPane();
                Label letterLabel = new Label();
                letterLabel.setVisible(false);
                slotPane.getChildren().add(letterLabel);
                slotPane.setStyle(" -fx-background-color: #eefcf9;\n" +
                                          "    -fx-border-color: #08FBE1;\n" +
                                          "    -fx-border-radius: 3px;\n" +
                                          "    -fx-background-radius: 3px;\n" +
                                          "    -fx-border-width: 2px;");
                phraseGridpane.add(slotPane,j,i);
            }

        }
        setNewPhrase("ssx","Ciao, il mio nome è Michele Rovagnati!");
    }

    public void updatePhrase(String letter){
        StackPane node;
        Label label;
        for(int i=0; i<5; i++){
            for(int j=0; j<14; j++){
                node = (StackPane)getNodeByRowColumnIndex(i,j);
                label = (Label)node.getChildren().get(0);
                if(label.getText().equals(letter)){
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

    public void updatePhrase(boolean[] phrase){
        int column = 0;
        int row = 0;
        int i = 0;
        StackPane node;
        Label label;
        while(i<phrase.length){
            node = (StackPane)getNodeByRowColumnIndex(row,column);
            label = (Label)node.getChildren().get(0);
            if(!label.isVisible()){
                if(phrase[i] == true){
                    label.setVisible(true);
                    node.setStyle(" -fx-background-color: #d6e2e0;\n" +
                            "    -fx-border-color: #08FBE1;\n" +
                            "    -fx-border-radius: 3px;\n" +
                            "    -fx-background-radius: 3px;\n" +
                            "    -fx-border-width: 2px;");
                    i++;
                    if(column<13){
                        column++;
                    }else{
                        column = 0;
                        row++;
                    }
                }else{
                    i++;
                    if(column<13){
                        column++;
                    }else{
                        column = 0;
                        row++;
                    }
                }
            }else {
                if(node.getStyle().equals(" -fx-background-color: #d6e2e0;\n" +
                        "    -fx-border-color: #08FBE1;\n" +
                        "    -fx-border-radius: 3px;\n" +
                        "    -fx-background-radius: 3px;\n" +
                        "    -fx-border-width: 2px;")){
                    i++;
                    if(column<13){
                        column++;
                    }else{
                        column = 0;
                        row++;
                    }
                }else{
                    if(column<13){
                        column++;
                    }else{
                        column = 0;
                        row++;
                    }
                }
            }
        }
    }

    public void setNewPhrase(String theme, String phrase){
        phraseThemeLabel.setText(theme);
        int column = 0;
        int row = 0;
        char car;
        StackPane node;
        phrase = phrase.toUpperCase();
        StringTokenizer st = new StringTokenizer(phrase," ',!?.:;\"/()\\^<>-+*");
        String s = st.nextToken();
        Label label;
        int pointer = 0;
        for(int i=0; i<phrase.length(); i++){
            car = phrase.charAt(i);
            if(car != ' ' && car != ',' && car != '\'' && car != '.' && car != ':' && car != ';' && car != '?' && car != '!' && car != '"' && car != '/' && car != '\\' && car != '(' && car != ')'
                    && car != '^' && car != '<' && car != '>' && car != '-' && car != '+' && car != '*'){
                if(pointer == s.length()-1){
                    if(st.hasMoreTokens()) {
                        s = st.nextToken();
                        pointer = -1;
                    }
                }else if(pointer == 0){
                    if(s.length() > (14-column)){
                        column = 0;
                        row++;
                    }
                }
                node = (StackPane)getNodeByRowColumnIndex(row,column);
                label = (Label)node.getChildren().get(0);
                label.setText(""+car);
                node.setStyle(" -fx-background-color: #bbbebd;\n" +
                        "    -fx-border-color: #08FBE1;\n" +
                        "    -fx-border-radius: 3px;\n" +
                        "    -fx-background-radius: 3px;\n" +
                        "    -fx-border-width: 2px;");
                pointer++;
                if(column<13){
                    column++;
                }else{
                    column = 0;
                    row++;
                }
            }else{
                if(!(car == ' ' && column == 0)) {
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
                }else{

                }
            }
        }
    }

    private Node getNodeByRowColumnIndex (final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = phraseGridpane.getChildren();

        for (Node node : childrens) {
            if(phraseGridpane.getRowIndex(node) == row && phraseGridpane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


    public void hideAll() {
        jollyButton.setVisible(false);
        vowelButton.setVisible(false);
        spinButton.setVisible(false);
        solutionButton.setVisible(false);
        solutionTextField.setDisable(true);
        letterTextfield.setDisable(true);
    }
    public void disableAll(){
        jollyButton.setDisable(true);
        vowelButton.setDisable(true);
        spinButton.setDisable(true);
        solutionButton.setDisable(true);
        solutionTextField.setDisable(true);
        letterTextfield.setDisable(true);
    }

    public void activeAll(){
        jollyButton.setDisable(false);
        vowelButton.setDisable(false);
        spinButton.setDisable(false);
        solutionButton.setDisable(false);
        letterTextfield.setDisable(false);
    }

    public void runCountdown(int seconds) {
        timeSeconds = seconds;
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 timeSeconds-- ;
                timerLabel.setText(String.valueOf(timeSeconds));
                if (timeSeconds <= 0)
                    timeline.stop();
            }
        }));
        timeline.playFromStart();
    }


    public void giveSolution() throws RemoteException {
        match.askForSolution();
        solutionTextField.setDisable(false);
        runCountdown(10);
    }
    @FXML
    public void confirmSolution() throws RemoteException {
        String solution =solutionTextField.getText();
        match.giveSolution(solution);
    }

    public void wheelSpin(){
        try {
         wheelResult = match.wheelSpin();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (wheelResult != 0) runCountdown(5);
    }

    public void wheelResult(String result){
        resultLabel.setText(result);
    }
     @FXML
     public void onEnter() throws RemoteException {
        String letter =  letterTextfield.getText();
     if(spinButton.isPressed()){
           match.giveConsonant(letter,wheelResult);
        }else if(vowelButton.isPressed()){
            match.giveVocal(letter);
        }
     }
     public void giveVocal() throws RemoteException {
        match.askForVocal();
        runCountdown(5);
     }

     public void giveJolly() throws RemoteException {
        match.jolly();
     }

    public void setTurn(String nickName) throws RemoteException {
        if(nickName.equals(player1Label.getText())){
           player1Box.setStyle("");
           player2Box.setStyle("");
           player3Box.setStyle("");
        }else
            if(nickName.equals(player2Label.getText())){
            player1Box.setStyle("");
            player2Box.setStyle("");
            player3Box.setStyle("");
        }else
            if(nickName.equals(player3Label.getText())){
            player1Box.setStyle("");
            player2Box.setStyle("");
            player3Box.setStyle("");
        }
         if(!nickName.equals(client.getNickname())){
           disableAll();
         }
         runCountdown(5);
    }

    public void yourTurn(){
        activeAll();
    }

    public void notifyPlayerStats(int pos, String nickname, int partial, int total, int numJolly){
        switch(pos){
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

      public void vocalcallnotify(String nickname){
        String message = nickname+ " ha chiamato la vocale";
          Notifications notification = Notifications.create()
                                               .title("Mosse")
                                               .text(message)
                                               .hideAfter(Duration.seconds(2))
                                               .position(Pos.BASELINE_CENTER);
          notification.showInformation();
      }

      public void callSolutionNotify(String nickname){
        String message = nickname + " da la soluzione ";
          Notifications notification = Notifications.create()
                                               .title("Mosse")
                                               .text(message)
                                               .hideAfter(Duration.seconds(2))
                                               .position(Pos.BASELINE_CENTER);
          notification.showInformation();

      }
      public void jollyNotify(String nickname){
          String message = nickname + " ha giocato il jolly";
          Notifications notification = Notifications.create()
                                               .title("Mosse")
                                               .text(message)
                                               .hideAfter(Duration.seconds(2))
                                               .position(Pos.BASELINE_CENTER);
          notification.showInformation();

      }

    public void callLetterNotify(String nickname , String letter){
        String message = nickname + " ha scelto la lettera " + letter;
        Notifications notification = Notifications.create()
                                             .title("Mosse")
                                             .text(message)
                                             .hideAfter(Duration.seconds(2))
                                             .position(Pos.BASELINE_CENTER);
        notification.showInformation();

    }

    public static void setMatch(RemoteMatch matc){
    	match = matc;
	}

	public static void setObserver(boolean observer){
        isObserver = observer;
	}

	public void exitMatch(ActionEvent actionEvent) throws IOException {
        try {
            if (isObserver) {
                match.leaveMatchAsObserver(client);
            }else{
                match.leaveMatchAsPlayer(client);
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }finally{
            match = null;
            Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("tab_pane.fxml"));
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root);
            //   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle("Wheel of Fortune");
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage oldStage = (Stage) exitButton.getScene().getWindow();
            oldStage.hide();
        }
    }
}
