package sample.Table;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import sample.ConnectionController;
import sample.Controller;
import sample.Table.CardManager;

import javax.swing.*;
import java.util.ArrayList;

//SOMETHING TO COMMIT
public class PlayScreen {

    private int DIALOG_SIZE = 180;
    ArrayList<CardManager> opponentsCards;
    private static PlayScreen instance;
    Stage primaryStage;
    int myPlayerNumber = -1;
    int numberOfPlayers = 0;
    Button hitButton;
    Button standButton;


    int nowPlayerId ;
    String nowPlayerUsername;
    private PlayScreen(){

        getPreliminaryInfo();
        start();
        play();
    }

    public static PlayScreen getInstance(){
        if(instance == null){
            instance = new PlayScreen();
        }
        return instance;
    }

    private void getPreliminaryInfo(){
        myPlayerNumber = ConnectionController.getInstance().getSomeInt();
        numberOfPlayers = ConnectionController.getInstance().getSomeInt();

        System.out.println(myPlayerNumber);
        System.out.println(numberOfPlayers);
    }

    private void start(){

        //Basic Pane
        primaryStage = new Stage();
        primaryStage.setTitle("JavaFX 2 Login");
        GridPane bp = new GridPane();
        bp.setPadding(new Insets(30,30,70,30));
        bp.setHgap(10);
        bp.setVgap(5);


        //Adding Panes
        /*
        GridPane chatPane = ininializationChat();

        BorderPane gridPaneChat = new BorderPane();
        gridPaneChat.setPrefSize(300,1400);
        gridPaneChat.setBottom(chatPane);

        bp.add(gridPaneChat,1,0,1,2);
           */
        GridPane tablePane = initializationTable();
        bp.add(tablePane,0,0);

        HBox boardPane = initializationBoard();
        bp.add(boardPane, 0, 1);


        //Add ID's to Nodes
        tablePane.setId("table");
        boardPane.setId("root");
        bp.setId("bp");

        //Adding BorderPane to the scene and loading CSS

        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("resources/table.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack");
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));

        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void play(){


        for(int i = 0; i < numberOfPlayers; i++) {



            new SwingWorker<Void,Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // asteapta raspuns de la server
                    nowPlayerId = ConnectionController.getInstance().getSomeInt();
                    nowPlayerUsername = ConnectionController.getInstance().getSomeText();
                    return null;
                }

                @Override
                protected void done() {
                    super.done();
                    if (nowPlayerId == myPlayerNumber) {
                        hitButton.setDisable(false);
                        standButton.setDisable(false);

                        System.out.println("Sunt aici");


                        if(standButton.isPressed()) {
                            hitButton.setDisable(true);
                            standButton.setDisable(true);
                        }
                    }

                    //afiseaza gui
                }
            }.execute();


        }
    }

    private GridPane initializationTable(){
        GridPane tablePane = new GridPane();
        //tablePane.setGridLinesVisible(true);
        tablePane.setPrefSize(1200, 700); // CU ASTEA NU NE JUCAM

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(40);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(15);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(15);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(15);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(15);
        tablePane.getRowConstraints().addAll(row1,row2,row3,row4,row5);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(16.66);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(16.66);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(16.66);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(16.66);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(16.66);
        ColumnConstraints column6 = new ColumnConstraints();
        column6.setPercentWidth(16.66);
        tablePane.getColumnConstraints().addAll(column1,column2,column3,column4,column5,column6);

        opponentsCards = new ArrayList<>();

        int x = 3;
        for(int i = 0; i < numberOfPlayers; i++){
            CardManager cartiOponent = new CardManager(0,5,0,0);
            cartiOponent.setPadding(new Insets(5));
            if(i < 3)
                tablePane.add(cartiOponent,i,i+1,1,2);
            else {
                tablePane.add(cartiOponent, i, x, 1, 2);
                x--;
            }
            opponentsCards.add(cartiOponent);
        }

        AnchorPane ap = new AnchorPane();
        ImageView dealerDeck = new ImageView(new Image(getClass().getResourceAsStream("/resources/deck/back.png")));

        dealerDeck.setFitHeight(145.20);
        dealerDeck.setFitWidth(100);

        ap.getChildren().add(dealerDeck);

        CardManager cartiDealer = new CardManager(0,5,0,0);

        tablePane.add(ap, 3,0,1,1);
        tablePane.add(cartiDealer,2,0,1,1);

        return tablePane;
    }

    HBox initializationBoard(){
        HBox localBoardPane = new HBox();
        localBoardPane.setSpacing(10);
        localBoardPane.setPrefSize(1200, 200);

        //ID
        GridPane idInfo = new GridPane();
        idInfo.setPrefSize(150,200);
        idInfo.setId("myMessagePane");

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/profile_picture.jpg"),100,100,true,true));
        image.setFitHeight(100);
        image.setFitWidth(100);
        idInfo.add(image,0,0);

        Label userName = new Label(opponentsCards.get(myPlayerNumber - 1).getUsername());
        userName.setAlignment(Pos.CENTER);
        userName.setStyle("-fx-text-fill:  #ffffff;");
        userName.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        idInfo.add(userName,0,1);
        idInfo.setAlignment(Pos.CENTER);

        localBoardPane.getChildren().add(idInfo);


        //CARDS

        CardManager carti = opponentsCards.get(myPlayerNumber-1);

        HBox.setHgrow(carti, Priority.ALWAYS);
        carti.setPadding(new Insets(5));
        carti.setId("myMessagePane");
        localBoardPane.getChildren().add(carti);

        //BUTTONS

        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setVgap(10);
        buttonPane.setPadding(new Insets(5,5,5,5));

        hitButton = new Button("Hit!");
        hitButton.setPrefSize(200, 50);
        hitButton.setAlignment(Pos.CENTER);
        hitButton.setId("btnSendMessage");
        hitButton.setDisable(true);

        standButton = new Button("Stand!");
        standButton.setPrefSize(200, 50);
        standButton.setAlignment(Pos.CENTER);
        standButton.setId("btnSendMessage");
        standButton.setDisable(true);

        hitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("ai dat click pe hit");
                opponentsCards.get(myPlayerNumber - 1).addCard(ConnectionController.getInstance().requestHit());
                int total = ConnectionController.getInstance().getSomeInt();
                System.out.println(total);
                opponentsCards.get(myPlayerNumber - 1).setTotal(total);
                if(total >= 21){
                    standButton.fire();
                }
            }
        });

        standButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ConnectionController.getInstance().requestStand();
                standButton.setVisible(false);
                hitButton.setVisible(false);
            }
        });

        buttonPane.add(hitButton,0,0);
        buttonPane.add(standButton,0,1);

        buttonPane.setId("myMessagePane");

        localBoardPane.getChildren().add(buttonPane);

        return localBoardPane;
    }
}
