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
import java.util.ArrayList;


/**
 * Questa classe gestisce l'aggiunta di nuove frasi all'interno del database
 */
public class PhraseManager {
    private DBManager dbManager;
    private static PhraseManager phraseManager = null;

    private PhraseManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @param dbmng il riferimento a {@link DBManager}
     * @return Il riferimento al singleton di {@link PhraseManager}
     */
    public static PhraseManager createPhraseManager(DBManager dbmng){
        if(phraseManager == null){
            phraseManager = new PhraseManager(dbmng);
            return phraseManager;
        }else{
            return phraseManager;
        }
    }

    /**
     * Questo metodo aggiunge al database le frasi ottenute attraverso un file di tipo .csv
     *
     * @param file il file da leggere
     * @return <code>true</code> se l'inserimento avviene con successo, <code>false</code> altrimenti
     * @throws IOException in caso di errori nella lettura del file
     */
    public boolean addPhrases(File file) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file.getName()));
        ArrayList<PhrasesDTO> phrases = new ArrayList<>();
        String[] nextLine;
        while((nextLine = reader.readNext()) != null){
            phrases.add(new PhrasesDTO(nextLine[0],nextLine[1]));
        }
        return dbManager.addPhrases(phrases);
    }
}
