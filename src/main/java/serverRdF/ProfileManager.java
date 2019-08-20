package serverRdF;

import serverRdF.dbComm.DBManager;

public class ProfileManager {
    private DBManager dbManager;
    private static ProfileManager profileManager = null;

    private ProfileManager(DBManager dbmng){
        dbManager = dbmng;
    }

    /**
     * @param dbmng il riferimento a {@link DBManager}
     * @return Il riferimento al singleton di {@link ProfileManager}
     */
    public static ProfileManager createProfileManager(DBManager dbmng){
        if(profileManager == null){
            profileManager = new ProfileManager(dbmng);
            return profileManager;
        }else{
            return profileManager;
        }
    }

    //TODO metodi
}
