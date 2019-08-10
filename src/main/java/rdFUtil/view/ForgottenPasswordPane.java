package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import serverRdF.Server;

public class ForgottenPasswordPane {
	Server server;

	public ForgottenPasswordPane(Server server) {

		this.server = server;
	}

	public void enter(ActionEvent event){
		Notifications notification = Notifications.create();
		notification.title("Mail Notification");
		notification.text("E-mail già presente \nimmettere nuova mail");
		notification.hideAfter(Duration.seconds(3));
		notification.position(Pos.CENTER);
		notification.showError();
	}
}
