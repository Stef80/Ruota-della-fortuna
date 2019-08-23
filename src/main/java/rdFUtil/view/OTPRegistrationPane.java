package rdFUtil.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.registrationRdF.OTPHelper;

import java.io.IOException;
import java.rmi.RemoteException;

public class OTPRegistrationPane {
    @FXML
    private TextField otpTextField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label timeLabel;
    private int timeSeconds = 0;
    private int timeMinutes = 10;
    private Timeline timeline;
    private Server server;
    private Client client;
    private OTPHelper otp;

    public OTPRegistrationPane(){}

    public OTPRegistrationPane(Server server, Client client, OTPHelper otp) {
        this.server = server;
        this.client = client;
        this.otp = otp;
    }

    public void enter() throws IOException {
        String otpStr = otpTextField.getText();
        boolean check = false;
        try {
            check = otp.checkOTP(otpStr, client);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (!check) {
            Notifications notification = Notifications.create()
                    .title("OTP Notification")
                    .text("CodiceOTP non valido\n")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER);
            notification.showError();
        } else {
            timeline.stop();
            Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource("main_pane.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle("Wheel of Fortune");
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage thisStage = (Stage) confirmButton.getScene().getWindow();
            thisStage.close();
        }
    }

    public void runCountdown() {
        timeLabel.setText(String.valueOf(timeMinutes) + ":" + String.valueOf(timeSeconds));
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (timeSeconds == 0) {
                    timeMinutes--;
                    timeSeconds = 59;
                }
                timeSeconds--;
                timeLabel.setText(String.valueOf(timeMinutes) + ":" + String.valueOf(timeSeconds));
                if (timeSeconds <= 0 && timeMinutes <= 0) {
                    timeline.stop();
                    Notifications notification = Notifications.create()
                            .title("OTP Notification")
                            .text("Tempo esaurito \nripeti la procedura di registrazione")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.CENTER);
                    notification.showError();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Thread.currentThread().getClass().getResource("main_pane.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    primaryStage.setTitle("Wheel of Fortune");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    Stage thisStage = (Stage) confirmButton.getScene().getWindow();
                    thisStage.close();
                }
            }
        }));
        timeline.playFromStart();
    }
}
