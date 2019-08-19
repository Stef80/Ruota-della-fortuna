package serverRdF.matchRdF;

import rdFUtil.client.Client;
import serverRdF.Server;
import serverRdF.ServerImplementation;

import java.rmi.RemoteException;

/**
 * Questa classe gestisce il timer per le mosse. Se il tempo finisce viene segnalato l'errore
 */
public class MoveTimer extends Thread {
    private int time;
    private Match match;
    private boolean isThisForJolly;

    public MoveTimer(int time, Match match, boolean jolly){
        this.time = time;
        this.match = match;
        isThisForJolly = jolly;
    }

    public boolean isThisForJolly() {
        return isThisForJolly;
    }

    public void run(){
        try{
            sleep(time);
            for(Client c : match.getObservers()){
                try {
                    c.notifyTimeOut();
                }catch(RemoteException e){
                    try {
                        match.leaveMatchAsObserver(c);
                    }catch(RemoteException ex){
                        ServerImplementation.serverError(null);
                    }
                }
            }
            for(Player p : match.getPlayers()){
                try {
                    p.getClient().notifyTimeOut();
                }catch(RemoteException e){
                    try {
                        match.leaveMatchAsPlayer(p);
                    }catch(RemoteException ex){
                        ServerImplementation.serverError(null);
                    }
                }
            }
            if (isThisForJolly) {
                match.errorInTurn(false);
            }else{
                match.errorInTurn(true);
            }
        }catch(InterruptedException e){
            if(isThisForJolly){
                match.getManche().getTurns().getLastMove().setOutCome(-1);
            }
            return;
        }
    }
}
