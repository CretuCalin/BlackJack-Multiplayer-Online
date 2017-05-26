package sample.Login;

/**
 * Created by teo on 15.04.2017.
 */
public class ControllerLoginScreen {

    static private ControllerLoginScreen instance;

    ControllerLoginScreen(){
        LoginScreen loginScreen = new LoginScreen();
    }

    public static ControllerLoginScreen getInstance(){
        if(instance == null){
            return new ControllerLoginScreen();
        }
        else
            return instance;
    }
}
