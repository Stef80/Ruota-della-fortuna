package rdFUtil;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.nio.charset.StandardCharsets;

public class Notification {
    /**
     * @param title il titolo della notifica
     * @param msg   il messaggio della notifica
     * @param error <code>true</code> se far visualizzare la notifica come errore, <code>false</code> altrimenti
     */
    public static void notify(String title, String msg, boolean error) {
        byte[] bTitle = title.getBytes();
        byte[] bMsg = msg.getBytes();
        int duration = 3;
        if (error) {
            System.out.println();
            Notifications.create()
                    .title(new String(bTitle, StandardCharsets.UTF_8))
                    .text(new String(bMsg, StandardCharsets.UTF_8))
                    .hideAfter(Duration.seconds(duration))
                    .position(Pos.BASELINE_RIGHT)
                    .showError();
        } else {
            System.out.println();
            Notifications.create()
                    .title(new String(bTitle, StandardCharsets.UTF_8))
                    .text(new String(bMsg, StandardCharsets.UTF_8))
                    .hideAfter(Duration.seconds(duration))
                    .position(Pos.BASELINE_RIGHT)
                    .showInformation();
        }
    }
}
