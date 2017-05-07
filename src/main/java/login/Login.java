package login;

import managers.DatabaseManager;
import managers.Manager;

import javax.xml.crypto.Data;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bobby on 27-04-2017.
 */
public class Login {


    private DatabaseManager database;
    public Login(DatabaseManager database) throws NoSuchAlgorithmException {

        this.database = database;

//        if(exists()){
//            //TEMPORARY
//            System.out.println("Logged in successful");
//        }else{
//            createUser();
//        }

//        database.close();
    }

    private boolean exists(String username, String password) throws NoSuchAlgorithmException {
        if (database.userExits(username,password))
            return true;
        return false;
    }

    private void createUser(String username, String password){

        if (database.usernameExits(username)){
            //TEMPORARY
            System.out.println("Username already exits.");
        }else{
            if (database.createNewUser(username,password ))
                //TEMPORARY
                System.out.println("New account created");
            else
                // TEMPORARY
                System.out.println("Failed to create a new account");
        }

    }

}
