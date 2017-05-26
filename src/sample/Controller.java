package sample;

import javafx.application.Application;
import sample.Login.LoginScreen;
import sample.MenuScreen.LobbyScreen;
import sample.Table.CardManager;
import sample.Table.PlayScreen;

/**
 * Created by Rares on 5/21/2017.
 */
public class Controller {
    public static Controller instance = null;
    static boolean t = true;

    Controller(String test){
        System.out.println(test);
        ConnectionController.getInstance();
        LoginScreen ls = new LoginScreen();
        ls.display();
    }

    public static Controller getInstance(){
        if(instance == null ) {
            instance = new Controller("din getinstance");
            t = false;
        }
        return instance;
    }

    public void createTable(String tableName, String numberOfPlayers, String timeOfMoves, boolean privacy, String password){
        ConnectionController.getInstance().createTable(tableName, numberOfPlayers, timeOfMoves, privacy, password);
        PlayScreen.getInstance();
    }

    public void hitRequest(CardManager cardManager){
        String response = ConnectionController.getInstance().requestHit();
        cardManager.addCard("/resources/deck/" + response + ".png");
    }

    public void standRequest(CardManager cardManager){
        String response = ConnectionController.getInstance().requestStand();
        cardManager.setResult(response);
    }
}
