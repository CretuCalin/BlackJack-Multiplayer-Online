package managers;

import login.Database;

import java.security.NoSuchAlgorithmException;


/**
 Created by calin on 30.04.2017.
 */
public class DatabaseManager {

    private Database database;

    private DatabaseManager(){

        database = new Database();
        database.connect();

    }

    private static DatabaseManager instance;

    protected static DatabaseManager getInstance(){
        if(instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    protected String userExits(String username, String password) throws NoSuchAlgorithmException {
        return database.userExits(username,password);
    }

    /*public boolean usernameExits(String username){
        return database.usernameExits(username);
    }*/

    protected boolean createNewUser(String username, String password){
        return database.createNewUser(username,password);
    }


    public void close(){
        database.close();
    }








}
