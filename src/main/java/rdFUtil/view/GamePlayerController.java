package rdFUtil.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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


    private Timeline timeline;
    private int timeSeconds;
    private int wheelResult;
    private RemoteMatch match;
    private Client client;

    public GamePlayerController(RemoteMatch match, Client client){
        this.match = match;
        this.client = client;
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

        public void hideButton() {
        exitButton.setVisible(false);
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
     public void giveVocal(){
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

}
