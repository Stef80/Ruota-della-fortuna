package PlayPack;

import javafx.collections.ObservableArray;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private List<String> playiers ;
	private boolean aviable;
	private String namePlayier;
	private String id;

	public String getNamePlayier() {
		return namePlayier;
	}

	public String getId() {
		return id;
	}

	public Game(String namePlayier, String id){
		this.namePlayier = namePlayier;
		this.id = id ;
		playiers = new ArrayList<>();
		aviable = true;
	}

	public List<String> getPlayiers() {
		return playiers;
	}

	public String getPlayer(int index){
		return playiers.get(index);
	}

	public boolean isAviable() {
		return aviable;
	}

	public void setPlayiers(String playier) {
		if(playiers.size() <= 3){
		this.playiers.add(playier);
		}else{
			System.out.println("Limite partecipanti raggiunto");
		}
	}

	public void setAviable(boolean aviable) {
		this.aviable = aviable;
	}
}
