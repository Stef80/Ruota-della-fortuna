package serverRdF;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import serverRdF.dbComm.DBManager;
import serverRdF.emailRdF.EmailManager;
import serverRdF.registrationRdF.RegistrationManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

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

    public boolean addPhrases(File file) throws Exception{
        CSVReader reader = new CSVReader(new FileReader("yourfile.csv"));
        return false;
    }
    //TODO metodi
}
