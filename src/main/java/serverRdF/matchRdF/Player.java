package serverRdF.matchRdF;

import rdFUtil.client.Client;

public class Player {

    private Client client;
    private int points;
    private int partialPoints;
    private int numJolly;

    public Player(Client c){
        client = c;
        points = 0;
        partialPoints = 0;
        numJolly = 0;
    }

    public Client getClient(){
        return client;
    }

    public int getPoints(){
        return points;
    }

    public int getPartialPoints(){
        return partialPoints;
    }

    public int getNumJolly(){
        return numJolly;
    }

    public void addPoints(int score){
        points += score;
    }

    public void updateScore(int score){
        partialPoints += score;
    }

    public void addJolly(){
        numJolly += 1;
    }

    public void removeJolly(){
        numJolly -= 1;
    }

    public void partialPointsToZero(){
        partialPoints = 0;
    }

    public void setNumJollyToZero(){
        numJolly = 0;
    }

    //TODO metodi ereditati da Client
}
