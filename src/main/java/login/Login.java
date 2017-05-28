package login;

import managers.DatabaseManager;
import managers.Manager;

import javax.xml.crypto.Data;
import java.lang.ref.SoftReference;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bobby on 27-04-2017.
 */
public class Login {

    public Login() {}

    private String exists(String username, String password) throws NoSuchAlgorithmException {
       return Manager.getInstance().UserExists(username,password);
    }

    private String createUser(String username, String password) throws NoSuchAlgorithmException {

        /*if (Manager.getInstance().UsernameExists(username)){
            //TEMPORARY
            return "Username already exits.";
        }else{*/
            if (Manager.getInstance().CreateNewUser(username,password ))
                //TEMPORARY
                return "NEW ACCOUNT CREATED";
            else
                // TEMPORARY
                return "Failed to create a new account";
        //}

    }

    public String verify(String username, String password){
        try {
            String message = exists(username, password);
            if (message.equals("User Dosen't exist")){
                return createUser(username, password);
            }
            return message;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "An error has occurd";
        }
    }

}
