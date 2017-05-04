package managers;

import javax.xml.crypto.Data;

/**
 * Created by calin on 30.04.2017.
 */
public class Manager {

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private DatabaseManager databaseManager;
    // public managers etc ...





    private Manager instance = null;

    protected Manager(){
        databaseManager = new DatabaseManager();
    }

    public Manager getInstance(){
        if(instance == null){
            instance = new Manager();
        }

        return instance;
    }



}
