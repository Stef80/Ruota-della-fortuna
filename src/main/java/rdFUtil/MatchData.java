package rdFUtil;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * è una classe di supporto utilizzata dal client e da MatchVisualizer per l'invio delle informazioni dei match senza dover passare al client gli oggetti remoti
 */
public class MatchData {
    private String Player1;
    private String Player2;
    private String Player3;
    private String date;
    private String time;
    private int numManche;
    private boolean onGoing;

    public String getPlayer1() {
        return Player1;
    }

    public String getPlayer2() {
        return Player2;
    }

    public String getPlayer3() {
        return Player3;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getNumManche() {
        return numManche;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public void setPlayer1(String player1) {
        Player1 = player1;
    }

    public void setPlayer2(String player2) {
        Player2 = player2;
    }

    public void setPlayer3(String player3) {
        Player3 = player3;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumManche(int numManche) {
        this.numManche = numManche;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public MatchData(){}
}
