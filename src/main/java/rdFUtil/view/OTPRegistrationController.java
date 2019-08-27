package rdFUtil.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Controller della finestra per l'inserimento di dell'OTP necessario al completamento della registrazione. Possiede un timer di dieci minuti oltre il
 *  quale la registrazione viene annullata
 */
public class OTPRegistrationController implements Initializable {
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

    public OTPRegistrationController(){}

    public OTPRegistrationController(Server server, Client client, OTPHelper otp) {
        this.server = server;
        this.client = client;
        this.otp = otp;
    }

    /**
     * Controlla che l'OTP inserito sia uguale a quello inviato via email. Se i due codici corrispondono, si viene reindirizzati alla schermata di login
     *
     * @throws IOException In caso non sia possibile accedere alla finestra successiva
     */
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
                    .position(Pos.BASELINE_RIGHT);
            notification.showError();
        } else {
            timeline.stop();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main_pane.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle(FrameTitle.main);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage thisStage = (Stage) confirmButton.getScene().getWindow();
            thisStage.close();
        }
    }

    /**
     * Avvia il timer di dieci minuti. Quando il temnpo scade, si viene reindirizzati alla schermata di login e viene segnalato il time out
     */
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
                            .position(Pos.BASELINE_RIGHT);
                    notification.showError();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("main_pane.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    primaryStage.setTitle(FrameTitle.main);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    Stage thisStage = (Stage) confirmButton.getScene().getWindow();
                    thisStage.close();
                }
            }
        }));
        timeline.playFromStart();
    }

    /**
     * Notifica che l'OTP inserito non corrisponde con quello che e' stato inviato via email.
     */
    public void notifyWrongOTP(){
        Notifications notification = Notifications.create()
                                             .title("OTP Notification")
                                             .text("OTP inserito errato \nriprova")
                                             .hideAfter(Duration.seconds(3))
                                             .position(Pos.BASELINE_RIGHT);
        notification.showError();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RegistrationFormController.setOTP(this);
        runCountdown();
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setOtp(OTPHelper otp) {
        this.otp = otp;
    }
}
