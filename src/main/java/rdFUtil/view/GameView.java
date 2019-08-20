package rdFUtil.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import rdFUtil.MatchData;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;
import java.io.IOException;
import java.rmi.RemoteException;

public class GameView extends ListCell<RemoteMatch> {
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label aviableLabel;
	@FXML
	private Button observeButton;
	@FXML
	private Button joinButton;

	private FXMLLoader loader;
	private Parent pane;
	private Server server;
	private Client client;
	private RemoteMatch match;

	public GameView(Server server, Client client, RemoteMatch match) {
		this.server = server;
		this.client = client;
		this.match = match;
	}

	@Override
	protected void updateItem(RemoteMatch item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("game_list_pane.fxml"));
				loader.setController(this);
				try {
					pane = loader.load();
					Scene scene = new Scene(pane);
					//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			MatchData players = null;
			try {
				players = item.createMatchData();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			label1.setText(players.getPlayer1());

			try {
				setAviableLabel(item.isAviable());
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			MatchData finalPlayers = players;
			joinButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
<<<<<<< HEAD
						server.joinMatch(client, item.getMatchId() );
=======
						match = server.joinMatch(client, item.getMatchId());
						boolean aviable = match.addPlayer(client);
						setAviableLabel(aviable);
						if(label2 == null){
							label2.setText(finalPlayers.getPlayer2());
						}else{
							label3.setText(finalPlayers.getPlayer3());
						}

>>>>>>> parent of d8b696f... aggiunta metodi senza argomenti
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindowPane.fxml"));
					Parent root = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					GamePlayerController game = loader.getController();
					game.hideButton();
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});
			observeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent event) {
					try {
						match.addObserver(client);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindowPane.fxml"));
					Parent root = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					GamePlayerController game = loader.getController();
					game.hideAll();
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});

			setGraphic(pane);
		}

	}

	/**
	 *
	 */
	private void setAviableLabel(boolean aviable) {
		if (aviable) {
			aviableLabel.setText("aviable");
		} else {
			aviableLabel.setText(null);
			joinButton.setDisable(true);
		}
	}

}
