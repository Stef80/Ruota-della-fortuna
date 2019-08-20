package rdFUtil.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.matchRdF.RemoteMatch;

import javax.swing.text.html.ImageView;
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
	private Label validateLabel;
	@FXML
	private Button observeButton;
	@FXML
	private Button joinButton;
	@FXML
	private ImageView weel;
	@FXML
	private Button jollyButton;

	private FXMLLoader loader;
	private Parent pane;
	private Server server;
	private Client client;

	public GameView(Server server) {
		this.server = server ;
	}
	public GameView(){}

	@Override
	protected void updateItem(RemoteMatch item, boolean empty) {
		super.updateItem(item, empty);

		if(empty || item == null) {
			setText(null);
			setGraphic(null);
		}else{
			if(loader == null){
				loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("game_list_pane.fxml"));
                 loader.setController(this);
				try {
					  pane = loader.load();
					  Scene scene = new Scene(pane);
				//	scene.getStylesheets().add(getClass().getResource("/sample/resources/sampleScene.css").toExternalForm());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		/*	label1.setText(item.getNamePlayier());
			label2.setText(item.getId());
			if(item.isAviable()){
				validateLabel.setText("aviable");
			}else{
				validateLabel.setText(null);
			}*/
			joinButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent event) {
					try {
<<<<<<< HEAD
						match = server.joinMatch(client, item.getMatchId());
						boolean aviable = match.addPlayer(client);
						setAviableLabel(aviable);
						if((label2.getText()).equals("id2")){
							label2.setText(finalPlayers.getPlayer2());
						}else{
							label3.setText(finalPlayers.getPlayer3());
						}

=======
						server.joinMatch(client, item.getMatchId() );
>>>>>>> 2f3d956de427ec6cdfe7a11882507974e68e78a9
					} catch (RemoteException e) {
						e.printStackTrace();
					}
			/*		FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindowPane.fxml"));
					Parent root  = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					GameWindowPane gameWindowPane = loader.getController();
					gameWindowPane.hideButton();
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
			*/	}
			});
			observeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent event) {
				/*	FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindowPane.fxml"));
					Parent root  = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    GameWindowPane gameWindowPane = loader.getController();
					gameWindowPane.setAllDisable();
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				*/}
			});

			setGraphic(pane);
		}

	}

}
