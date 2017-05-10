package managers;

import java.security.NoSuchAlgorithmException;

/**
  Created by calin on 30.04.2017.
 */
public class Manager {

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private DatabaseManager databaseManager;

    private LoginManager loginManager;

    private TablesManager tablesManager;

    public LoginManager getLoginManager() {
        return loginManager;
    }

    // public managers etc ...


    private static Manager instance = null;

    protected Manager() throws NoSuchAlgorithmException {
        databaseManager = DatabaseManager.getInstance();
        loginManager = LoginManager.getInstance();
        tablesManager = TablesManager.getInstance();
    }

    public static Manager getInstance () throws NoSuchAlgorithmException {
        if(instance == null){
            instance = new Manager();
        }
        return instance;
    }

    public String UserExists(String unsername, String password) throws NoSuchAlgorithmException {
        return databaseManager.userExits(unsername,password);
    }

    public TablesManager getTablesManager() {
        return tablesManager;
    }

    public boolean CreateNewUser(String username, String password){
        return databaseManager.createNewUser(username, password);
    }



}
