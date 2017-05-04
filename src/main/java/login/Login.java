package login;

import javax.xml.crypto.Data;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bobby on 27-04-2017.
 */
public class Login {

    private String username;
    private String password;
    private Database database;


    public Login(){

    }

    public Login(String user,String pass) throws NoSuchAlgorithmException {
        this.username=user;
        this.password=pass;

        this.database = new Database();

        if(exists()){
            //TEMPORARY
            System.out.println("Logged in successful");
        }else{
            createUser();
        }

        database.close();
    }

    private boolean exists() throws NoSuchAlgorithmException {
        if (database.userExits(username,password))
            return true;
        return false;
    }

    private void createUser(){

        if (database.usernameExits(this.username)){
            //TEMPORARY
            System.out.println("Username already exits.");
        }else{
            if (database.createNewUser(this.username,this.password  ))
                //TEMPORARY
                System.out.println("New account created");
            else
                // TEMPORARY
                System.out.println("Failed to create a new account");
        }

    }

}
