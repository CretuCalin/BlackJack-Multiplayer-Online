package sample.Login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ConnectionController;
import sample.MenuScreen.LobbyScreen;

public class LoginScreen {



    private Button btnLogin;
    private Label linkSignUp;
    private Label forgotPassword;
    TextField txtUserName;
    PasswordField pf;
    Stage primaryStage;


    public void display(){
        primaryStage = new Stage();
        primaryStage.setTitle("Login Screen");
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(120,300,160,300));
        bp.setPrefSize(1600,870);

        //Adding HBox

        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(7);


        Text text = new Text("Play Multiplayer");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        hb.getChildren().add(text);
        bp.setTop(hb);


        //Adding GridPane



        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(1000,500);
        gridPane.setHgap(10);
        gridPane.setVgap(5);


        //Implementing Nodes for GridPane



        Label lblTitle = new Label(" Blackjack");
        lblTitle.setFont(Font.font("Script MT Bold", FontWeight.BOLD, 60));
        lblTitle.setTextFill(Color.web("663F15"));

        Label textUsername = new Label("Username");
        textUsername.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        textUsername.setTextFill(Color.web("663F15"));

        txtUserName = new TextField();
        txtUserName.setFocusTraversable(false);
        txtUserName.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));

        Label textPassword = new Label("Password");
        textPassword.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        textPassword.setTextFill(Color.web("663F15"));

        pf = new PasswordField();
        pf.setFocusTraversable(false);
        pf.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));

        GridPane gridPaneForgotPassword = new GridPane();
        gridPaneForgotPassword.setPrefSize(1000,500);
        gridPaneForgotPassword.setHgap(10);
        gridPaneForgotPassword.setVgap(5);

        forgotPassword = new Label("Forgot password?");
        forgotPassword.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
        forgotPassword.setTextFill(Color.web("663F15"));
        forgotPassword.setUnderline(true);
        gridPaneForgotPassword.add(forgotPassword, 18, 0);


        btnLogin = new Button("Login");
        btnLogin.setTextFill(Color.web("ffffff"));
        btnLogin.setFont(Font.font("Courier New", FontWeight.BOLD, 16));

        GridPane gridPaneSignUp = new GridPane();
        gridPaneSignUp.setPrefSize(1000,500);
        gridPaneSignUp.setHgap(5);
        gridPaneSignUp.setVgap(5);

        final Label messageSignUp = new Label("Don't have an account yet?");
        messageSignUp.setFont(Font.font("Courier New", FontWeight.NORMAL, 13));
        messageSignUp.setTextFill(Color.web("663F15"));

        linkSignUp = new Label("Sign Up");
        linkSignUp.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        linkSignUp.setTextFill(Color.web("663F15"));


        gridPaneSignUp.add(messageSignUp, 3, 0);
        gridPaneSignUp.add(linkSignUp, 4, 0);


        //Adding Nodes to GridPane layout
        gridPane.add(lblTitle, 64, 4);
        gridPane.add(textUsername, 64, 13);
        gridPane.add(txtUserName, 64, 15);
        gridPane.add(textPassword, 64, 17);
        gridPane.add(pf, 64, 19);
        gridPane.add(gridPaneForgotPassword,64,21);
        gridPane.add(btnLogin, 64, 32);
        gridPane.add(gridPaneSignUp,64,50);





        //Add ID's to Nodes
        bp.setId("bp");
        txtUserName.setId("textField");
        pf.setId("textField");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");



        //Actions for buttons and texts


        btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(loginAllowed()){
                    LobbyScreen.getInstance();
                }
            }
        });

        forgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Forgot Password");
            }
        });

        linkSignUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Sign Up");
            }
        });

        btnLogin.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.getScene().setCursor(Cursor.HAND); //Change cursor to hand
            }
        });

        btnLogin.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });

        forgotPassword.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.getScene().setCursor(Cursor.HAND); //Change cursor to hand
            }
        });

        forgotPassword.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });

        linkSignUp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.getScene().setCursor(Cursor.HAND); //Change cursor to hand
            }
        });

        linkSignUp.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });




        //AGridPane layout to BorderPane Layout

        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("resources/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack");
        // primaryStage.setMaximized(true);
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private boolean loginAllowed(){
        String response = ConnectionController.getInstance().sendLoginData(txtUserName.getText(),pf.getText());
        System.out.println(response);
        while(!response.equals("NEW ACCOUNT CREATED") && !response.equals("CORRECT AUTHENTICATION")){
            response = ConnectionController.getInstance().sendLoginData(txtUserName.getText(),pf.getText());
        }
        primaryStage.hide();
        return true;

    }




}
