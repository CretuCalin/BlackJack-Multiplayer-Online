package managers;

import javax.xml.crypto.Data;
import java.security.NoSuchAlgorithmException;

/**
 * Created by calin on 30.04.2017.
 */
public class Manager {

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private DatabaseManager databaseManager;

    public LoginManager getLoginManager() {
        return loginManager;
    }

    private LoginManager loginManager;
    // public managers etc ...



    private static Manager instance = null;

    protected Manager() throws NoSuchAlgorithmException {
        databaseManager = new DatabaseManager();
        loginManager = new LoginManager();
    }

    public static Manager getInstance () throws NoSuchAlgorithmException {
        if(instance == null){
            instance = new Manager();
        }
        return instance;
    }



}
