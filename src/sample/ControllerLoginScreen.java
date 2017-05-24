package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import sample.LobbyScreen;
import sample.LoginScreen;

import java.awt.event.ActionListener;

/**
 * Created by teo on 15.04.2017.
 */
public class ControllerLoginScreen implements EventHandler<ActionEvent> {

    private LoginScreen loginScreen;




    public void clickLogin(){


        System.out.println("Login");
        LobbyScreen gui = new LobbyScreen();
        gui.display();
        Stage stage = (Stage) loginScreen.getForgotPassword().getScene().getWindow();
        stage.close();
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Loginnnnnnnnnnnnnnn");
        LobbyScreen gui = new LobbyScreen();
        gui.display();
        Stage stage = (Stage) loginScreen.getForgotPassword().getScene().getWindow();
        stage.close();
    }
}
