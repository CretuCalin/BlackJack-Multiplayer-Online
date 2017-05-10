package managers;

import login.Login;
import pojo.User;

import java.security.NoSuchAlgorithmException;

/**
 * Created by calin on 04.05.2017.
 */
public class LoginManager {

    private Login login;

    private static LoginManager instance;

    public static LoginManager getInstance() throws NoSuchAlgorithmException {
        if(instance == null)
            instance = new LoginManager();
        return instance;
    }

    private LoginManager() throws NoSuchAlgorithmException {
        login = new Login();
    }

    public String newPlayer(User user){
        return login.verify(user.getUsername(), user.getPassword());
    }





}
