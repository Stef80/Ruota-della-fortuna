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
    private boolean isThisForSolution;

    public MoveTimer(int time, Match match, boolean jolly, boolean solution){
        this.time = time;
        this.match = match;
        isThisForJolly = jolly;
        isThisForSolution = solution;
    }

    public boolean isThisForSolution() {
        return isThisForSolution;
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
                match.errorInTurn(false,true);
            }else{
                match.errorInTurn(true,false);
            }
        }catch(InterruptedException e){
            return;
        }
    }
}
