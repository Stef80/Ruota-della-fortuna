package rdFUtil;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.nio.charset.StandardCharsets;

public class Notification {
    public static void notification(String title, String msg, int duration, boolean error) {
        byte[] bTitle = title.getBytes();
        byte[] bMsg = msg.getBytes();
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
