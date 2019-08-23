package rdFUtil.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;

import java.rmi.RemoteException;

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
    private TextField letterTextField;
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

        for (int i = 0; i < 4; i++) {
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
    }

    public void hideAll() {
        jollyButton.setVisible(false);
        vowelButton.setVisible(false);
        spinButton.setVisible(false);
        solutionButton.setVisible(false);
        solutionTextField.setDisable(true);
        letterTextField.setDisable(true);
    }
    public void disableAll(){
        jollyButton.setDisable(true);
        vowelButton.setDisable(true);
        spinButton.setDisable(true);
        solutionButton.setDisable(true);
        solutionTextField.setDisable(true);
        letterTextField.setDisable(true);
    }

    public void activeAll(){
        jollyButton.setDisable(false);
        vowelButton.setDisable(false);
        spinButton.setDisable(false);
        solutionButton.setDisable(false);
        letterTextField.setDisable(false);
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
        String letter =  letterTextField.getText();
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

    // todo  chiamata scelta vocale , chiamata soluzione , giocata jolly , chiamata lettera
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
}
