package managers;

import login.Database;

import java.security.NoSuchAlgorithmException;


/**
 * Created by calin on 30.04.2017.
 */
public class DatabaseManager {

    Database database;

    public DatabaseManager(){

        database = new Database();
        database.connect();

    }

    public boolean userExits(String username, String password) throws NoSuchAlgorithmException {
        return database.userExits(username,password);
    }

    public boolean usernameExits(String username){
        return database.usernameExits(username);
    }

    public boolean createNewUser(String username, String password){
        return database.createNewUser(username,password);
    }



    public void close(){
        database.close();
    }








}
