package managers;

import login.Login;

import java.security.NoSuchAlgorithmException;

/**
 * Created by calin on 04.05.2017.
 */
public class LoginManager {

    private Login login;

    public LoginManager() throws NoSuchAlgorithmException {
        login = new Login(Manager.getInstance().getDatabaseManager());
    }



}
