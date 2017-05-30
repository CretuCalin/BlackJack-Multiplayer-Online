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

import pojo.Card;
import sample.ConnectionController;
import sample.Controller;
import sample.Table.CardManager;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

//SOMETHING TO COMMIT
public class PlayScreen {

    private int DIALOG_SIZE = 180;
    ArrayList<CardManager> opponentsCards = new ArrayList<>();
    private static PlayScreen instance;
    Stage primaryStage;
    int myPlayerNumber = -1;
    int numberOfPlayers = 0;
    int currentPlayer = 0;
    Button hitButton;
    Button standButton;
    boolean allInfo = true;
    boolean created = true;


    int nowPlayerId ;
    String nowPlayerUsername;
    private PlayScreen(){
        getPreliminaryInfo();
        play();
        start();
    }

    public void getPreliminaryInfo(){
        String message = ConnectionController.getInstance().getSomeText();
        myPlayerNumber = Character.getNumericValue(message.charAt(message.length() - 1));
        message = ConnectionController.getInstance().getSomeText();
        numberOfPlayers = Character.getNumericValue(message.charAt(message.length() - 1));

        System.out.println(myPlayerNumber);
        System.out.println(numberOfPlayers);

    }

    public static PlayScreen getInstance(){
        if(instance == null){
            instance = new PlayScreen();
        }
        return instance;
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
        System.out.println("aici e");

        GridPane tablePane = initializationTable();
        bp.add(tablePane,0,0);

        HBox boardPane = initializationBoard();
        bp.add(boardPane, 0, 1);

        created = false;


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
        //Map<Integer, Card> mapa = new Map<Integer, Card>;
        final Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {
                    Object messageReceived = ConnectionController.getInstance().read();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (messageReceived instanceof Card) {
                                while (created) {
                                }
                                //mapa.put(currentPlayer-1, (Card) messageReceived);
                                opponentsCards.get(currentPlayer - 1).addCard((Card) messageReceived);


                            } else if (messageReceived instanceof Integer) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Platform.runLater(() -> {
                                    opponentsCards.get(currentPlayer - 1).setTotal((int) messageReceived);
                                });

                            } else if (messageReceived instanceof String) {
                                String message = messageReceived.toString();
                                processString(message);
                            }
                        }
                    });
                }


            }
        });
        thread.start();

    }

    public void showButtons(){
        hitButton.setDisable(false);
        standButton.setDisable(false);
    }

    private void processString(String message)
    {
        boolean optionTester = true;
        /*if(message.contains("Your turn is") && optionTester == true)
        {
            char lastChar = message.;
            int turn = Character.getNumericValue(lastChar);
            System.out.println(message);
            System.out.println(turn);
            myPlayerNumber = turn;
            optionTester=false;
        }*/
        if(message.equals("Start") && optionTester == true){

            optionTester = false;
        }
        if((message.contains("Player") || message.equals("Dealer")) && optionTester == true)
        {
            if(message.contains("Player")){
                char lastChar = message.charAt(message.length() - 1);
                currentPlayer = Character.getNumericValue(lastChar);
            }
            else
                currentPlayer = 10;
            optionTester=false;
        }
        if(message.contains("Player turn") && optionTester == true){
            if(currentPlayer == numberOfPlayers)
            {
                showButtons();
            }
            optionTester = true;
        }

        if ((message.equals("You Win") || message.equals("You Lost") || message.equals("Draw")
                || message.equals("Dealer BUSTED! You Win!"))  && optionTester == true)
        {
            //gameFrame.updateInfoLabel(message);
            //gameFrame.hideButtons();
            //this.client.setRunning(false);
            optionTester=false;
        }
        if (message.equals("BUSTED") && optionTester == true)
        {
            //sendTotal("BUST");
            /*if(myTurn)
            {
                gameFrame.updateInfoLabel(message);
                gameFrame.hideButtons();
                myTurn = false;
                this.client.setRunning(false);
            }*/
            optionTester=false;
        }
        if (message.equals("Dealer's turn") && optionTester == true)
        {
            //sendCurrentPlayer("Dealer");
            optionTester=false;
        }
        if(message.contains("Your score is "))
        {
            System.out.println(message);
            //login.dispose();
            //gameFrame = new GameFrame(currentProcessor);
            //gameFrame.setVisible(true);
        }
        if(optionTester == true && !message.contains("Your score is "))
        {
            //client.sendMessage(message);
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

        //Label userName = new Label(opponentsCards.get(myPlayerNumber - 1).getUsername());
        //userName.setAlignment(Pos.CENTER);
        //userName.setStyle("-fx-text-fill:  #ffffff;");
        //userName.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        //idInfo.add(userName,0,1);
        //idInfo.setAlignment(Pos.CENTER);

        //localBoardPane.getChildren().add(idInfo);


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
                ConnectionController.getInstance().requestHit();
                //int total = ConnectionController.getInstance().getSomeInt();
                //System.out.println(total);
                //opponentsCards.get(myPlayerNumber - 1).setTotal(total);
                //if(total >= 21){
                    //standButton.fire();
                //}
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
