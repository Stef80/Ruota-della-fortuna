package serverRdF;

import serverRdF.dbComm.DBManager;
import serverRdF.emailRdF.EmailManager;
import serverRdF.registrationRdF.RegistrationManager;

public class PhraseManager {
    private DBManager dbManager;
    private static PhraseManager phraseManager = null;

    private PhraseManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public static PhraseManager createPhraseManager(DBManager dbmng){
        if(phraseManager == null){
            phraseManager = new PhraseManager(dbmng);
            return phraseManager;
        }else{
            return phraseManager;
        }
    }

    //TODO metodi
}
