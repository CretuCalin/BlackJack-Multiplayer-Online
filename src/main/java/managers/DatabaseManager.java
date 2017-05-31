package managers;

import login.Database;
import networking.PlayerCommunication;
import pojo.Table;
import pojo.TablesForClient;
import pojo.User;

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

    public void deleteUsers(ArrayList<PlayerCommunication> players){
        for (int i = 0; i < players.size(); i++){
            database.deleteUser(players.get(i).getUser().getUsername());
        }
    }

    public void deleteTable(int ID){
        database.deleteTable(ID);
        getTables();
    }


    public boolean createNewUser(String username, String password){
        return database.createNewUser(username,password);
    }

    public boolean addNewTable(Table table){
        return database.createNewTable(table);
    }

    public void addToTable(Table table, User user){
        database.addToTable(table, user);
    }

    public ArrayList<TablesForClient> getTables(){
        return database.getTables();
    }

    public void close(){
        database.close();
    }








}
