package managers;

import login.Database;


/**
 * Created by calin on 30.04.2017.
 */
public class DatabaseManager {

    Database database;

    public DatabaseManager(){

        database = new Database();
        database.connect();

    }

    public void close(){
        database.close();
    }








}
