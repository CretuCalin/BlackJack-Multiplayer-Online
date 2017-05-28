package managers;

import login.Database;
import pojo.Table;
import pojo.TablesForClient;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


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

    public static DatabaseManager getInstance(){
        if(instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public String userExits(String username, String password) throws NoSuchAlgorithmException {
        return database.userExits(username,password);
    }

    /*public boolean usernameExits(String username){
        return database.usernameExits(username);
    }*/

    public boolean createNewUser(String username, String password){
        return database.createNewUser(username,password);
    }

    public boolean addNewTable(Table table){
        return database.createNewTable(table);
    }

    public ArrayList<TablesForClient> getTables(){
        return database.getTables();
    }

    public void close(){
        database.close();
    }








}
