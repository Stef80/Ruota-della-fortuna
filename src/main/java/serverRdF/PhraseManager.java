package serverRdF;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import serverRdF.dbComm.DBManager;
import serverRdF.dbComm.PhrasesDTO;
import serverRdF.emailRdF.EmailManager;
import serverRdF.registrationRdF.RegistrationManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
        CSVReader reader = new CSVReader(new FileReader(file.getName()));
        ArrayList<PhrasesDTO> phrases = new ArrayList<>();
        String[] nextLine;
        while((nextLine = reader.readNext()) != null){
            phrases.add(new PhrasesDTO(nextLine[0],nextLine[1]));
        }
        return dbManager.addPhrases(phrases);
    }
    //TODO metodi
}
