package serverRdF.matchRdF;

import rdFUtil.client.Client;

import java.rmi.RemoteException;

public class Player {

    private Client client;
    private String nickname;
    private int points;
    private int partialPoints;
    private int numJolly;

    public Player(Client c) throws RemoteException{
        client = c;
        points = 0;
        partialPoints = 0;
        numJolly = 0;
        nickname = c.getNickname();
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

    public String getNickname(){
        return nickname;
    }


    //metodi ereditati da Client con la delega

    //TODO metodi ereditati da Client forse non necessari visto che è possibile ottenere direttamente il riferimento al Client
}
