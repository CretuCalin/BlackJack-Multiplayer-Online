package sample;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sample.Login.LoginScreen;
import sample.MenuScreen.LobbyScreen;
import sample.Table.CardManager;
import sample.Table.CardManager2;
import sample.Table.PlayScreen;
import sample.Table.PlayScreen2;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Rares on 5/21/2017.
 */
public class Controller {
    public static Controller instance = null;
    static boolean t = true;

    private Controller(String test){
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("Careful with the next step!");

        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConnectionController.getInstance().sendStartGame();
        }
        PlayScreen2.getInstance();
    }

    public void joinTable(String tableName,String password){
        try {
            ConnectionController.getInstance().joinTable(tableName,password);
        } catch (IOException e) {
            e.printStackTrace();
        }/*
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("Careful with the next step!");

        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ConnectionController.getInstance().sendStartGame();
        }
        PlayScreen.getInstance();
        */
        String message = (String) ConnectionController.getInstance().getSomeText();
        if(message.equals("GAME STARTED")){
            System.out.println("o sa plece");
            PlayScreen2.getInstance();
        }
    }

    public void standRequest(CardManager2 cardManager){
        String response = ConnectionController.getInstance().requestStand();
        //PlayScreen2.getInstance().setStatus(cardManager.response);
    }
}
