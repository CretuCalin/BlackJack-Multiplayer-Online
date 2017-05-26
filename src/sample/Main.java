package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Login.LoginScreen;

/**
 * Created by teo on 15.04.2017.
 */
public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.getInstance();
    }
}
