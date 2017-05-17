package sample;
import javafx.application.Application;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import sample.*;

import java.util.ArrayList;

//SOMETHING TO COMMIT
public class PlayScreen extends Application{

    private int DIALOG_SIZE = 180;

    @Override
    public void start(Stage primaryStage) throws Exception{


        //Basic Pane

        primaryStage.setTitle("JavaFX 2 Login");
        GridPane bp = new GridPane();
        //bp.setGridLinesVisible(true);
        bp.setPadding(new Insets(30,30,70,30));
        bp.setHgap(10);
        bp.setVgap(5);


        //Adding Panes

        GridPane chatPane = ininializationChat();

        BorderPane gridPaneChat = new BorderPane();
        gridPaneChat.setPrefSize(300,1400);
        gridPaneChat.setBottom(chatPane);

        bp.add(gridPaneChat,1,0,1,2);

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

        ArrayList<CardManager> opponentsCards = new ArrayList<>();

        int x = 3;
        for(int i = 0; i < 6; i++){
            CardManager cartiOponent = new CardManager(0,5,0,0);
            cartiOponent.setOnMouseClicked(event -> {
                cartiOponent.addCard("/resources/deck/8 of Hearts.png");
            });
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
        //ImageView dealerDeck = new ImageView(new Image(getClass().getResourceAsStream("")));

        dealerDeck.setFitHeight(170);
        dealerDeck.setFitWidth(117);

        ap.getChildren().add(dealerDeck);

        CardManager cartiDealer = new CardManager(0,5,0,0);
        cartiDealer.setOnMouseClicked(event -> {
            cartiDealer.addCard("/resources/deck/8 of Hearts.png");
        });

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

        Label userName = new Label("Adam Levine");
        userName.setAlignment(Pos.CENTER);
        userName.setStyle("-fx-text-fill:  #ffffff;");
        userName.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        idInfo.add(userName,0,1);
        idInfo.setAlignment(Pos.CENTER);

        localBoardPane.getChildren().add(idInfo);

        //MONEY
        GridPane cashPanel = new GridPane();
        cashPanel.setPrefSize(200,200);
        cashPanel.setId("myMessagePane");

        ImageView coinsImage = new ImageView(new Image(getClass().getResourceAsStream("/resources/coins.png"),100,100,true,true));
        coinsImage.setFitHeight(100);
        coinsImage.setFitWidth(100);
        cashPanel.add(coinsImage,0,0);

        Label moneyAmount = new Label("250$");
        moneyAmount.setAlignment(Pos.TOP_CENTER);
        moneyAmount.setStyle("-fx-text-fill:  #ffffff;");
        moneyAmount.setPrefSize(100, 15);
        moneyAmount.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        cashPanel.add(moneyAmount,0,1);
        cashPanel.setAlignment(Pos.CENTER);

        localBoardPane.getChildren().add(cashPanel);



        //CARDS

        //BorderPane carti = cardManager();
        CardManager carti = new CardManager(0,5,0,0);
        carti.setOnMouseClicked(event -> {
            carti.addCard("/resources/deck/8 of Hearts.png");
        });
        HBox.setHgrow(carti, Priority.ALWAYS);
        carti.setPadding(new Insets(5));
        carti.setId("myMessagePane");
        localBoardPane.getChildren().add(carti);
        //BUTTONS

        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setVgap(10);
        buttonPane.setPadding(new Insets(5,5,5,5));

        Button hitButton = new Button("Hit!");
        hitButton.setPrefSize(200, 50);
        hitButton.setAlignment(Pos.CENTER);
        hitButton.setId("btnSendMessage");

        Button standButton = new Button("Stand!");
        standButton.setPrefSize(200, 50);
        standButton.setAlignment(Pos.CENTER);
        standButton.setId("btnSendMessage");

        buttonPane.add(hitButton,0,0);
        buttonPane.add(standButton,0,1);

        buttonPane.setId("myMessagePane");

        localBoardPane.getChildren().add(buttonPane);

        return localBoardPane;
    }

    GridPane ininializationChat(){

        GridPane chatPane = new GridPane();
        //chatPane.setPrefSize(150,750);
        GridPane.setHgrow(chatPane, Priority.ALWAYS);
        chatPane.setHgap(10);
        chatPane.setVgap(10);

        VBox discution = new VBox();
        discution.setSpacing(10);
        discution.setPadding(new Insets(10));
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(100,900);
        sp.setContent(discution);
        sp.setPannable(true);
        sp.setVvalue(1.0);

        GridPane sentMessagePane = new GridPane();
        sentMessagePane.setPrefSize(275,160);
        sentMessagePane.setHgap(10);
        sentMessagePane.setVgap(5);


        GridPane myMessagePane = new GridPane();
        myMessagePane.setPrefSize(240,180);
        myMessagePane.setHgap(3);
        myMessagePane.setVgap(5);



        final TextField sendMessage = new TextField();
        sendMessage.setFocusTraversable(false);
        sendMessage.setPrefSize(210,180);

        Button sendButtonMessage = new Button("Send");



        myMessagePane.add(sendMessage,0,0);
        myMessagePane.add(sendButtonMessage,1,0);


        //ImageView image = new ImageView(new Image(getClass().getResourceAsStream("src/resources/profile_picture.jpg"),100,100,true,true));
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/profile_picture.jpg"),100,100,true,true));
        image.setFitHeight(40);
        image.setFitWidth(40);



        sentMessagePane.add(myMessagePane,0,0);
        sentMessagePane.add(image,1,0);
        chatPane.add(sp,0,0);
        chatPane.add(sentMessagePane,0,1);

        /////////////////////////////////////////////////////////////////////////

        //ALA DE USER
        GridPane messagePane = new GridPane();
        messagePane.setPrefWidth(DIALOG_SIZE);
        messagePane.setHgap(10);
        messagePane.setVgap(5);

        //ALA PORTOCALIU
        GridPane textMessagePane = new GridPane();
        textMessagePane.setPrefWidth(DIALOG_SIZE);
        textMessagePane.setHgap(10);
        textMessagePane.setVgap(1);




        GridPane dataPane = new GridPane();
        dataPane.setHgap(10);
        dataPane.setVgap(10);

        final Label name = new Label("Shakira");
        name.setFocusTraversable(false);
        name.setStyle("-fx-text-fill:  #ffffff;");
        name.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        final Label time = new Label("02:53");
        time.setFocusTraversable(false);
        time.setStyle("-fx-text-fill:  #ffffff;");
        time.setFont(Font.font("Courier New", FontWeight.BOLD, 14));


        dataPane.add(name,0,0);
        dataPane.add(time,1,0);

        final Label message = new Label();
        message.setFocusTraversable(false);
        message.setWrapText(true);
        message.setText("Hi, Adam");
        message.setStyle("-fx-text-fill:  #ffffff;");
        message.setFont(Font.font("Courier New", FontWeight.BOLD, 16));


        textMessagePane.add(dataPane,0,0);
        textMessagePane.add(message,0,1);

        ImageView imageProfileChat = new ImageView(new Image(getClass().getResourceAsStream("/resources/shakira.jpg"),100,100,true,true));
        imageProfileChat.setFitHeight(40);
        imageProfileChat.setFitWidth(40);

        messagePane.add(textMessagePane,1,0);
        messagePane.add(imageProfileChat,0,0);

        discution.getChildren().add(messagePane);



        ////////////////////////////////////////////////////////////////////////////




        sendButtonMessage.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                GridPane messagePane = new GridPane();
                messagePane.setPrefWidth(DIALOG_SIZE);
                messagePane.setHgap(10);
                messagePane.setVgap(5);

                GridPane textMessagePane = new GridPane();
                textMessagePane.setPrefWidth(DIALOG_SIZE);
                textMessagePane.setHgap(10);
                textMessagePane.setVgap(1);


                GridPane dataPane = new GridPane();
                dataPane.setHgap(10);
                dataPane.setVgap(10);

                final Label name = new Label("Adam Levine");
                name.setFocusTraversable(false);
                name.setStyle("-fx-text-fill:  #ffffff;");
                name.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

                final Label time = new Label("02:55");
                time.setFocusTraversable(false);
                name.setStyle("-fx-text-fill:  #ffffff;");
                time.setStyle("-fx-text-fill:  #ffffff;");
                time.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

                dataPane.add(name,0,0);
                dataPane.add(time,1,0);

                final Label message = new Label();
                message.setFocusTraversable(false);
                message.setWrapText(true);
                message.setText(sendMessage.getText());
                message.setStyle("-fx-text-fill:  #ffffff;");
                message.setFont(Font.font("Courier New", FontWeight.BOLD, 16));


                textMessagePane.add(dataPane,0,0);
                textMessagePane.add(message,0,1);
                textMessagePane.setId("myMessage");

                ImageView imageProfileChat = new ImageView(new Image(getClass().getResourceAsStream("/resources/profile_picture.jpg"),100,100,true,true));
                imageProfileChat.setFitHeight(40);
                imageProfileChat.setFitWidth(40);

                messagePane.add(textMessagePane,0,0);
                messagePane.add(imageProfileChat,1,0);


                discution.getChildren().add(messagePane);
                sendMessage.setText("");
                sp.setVvalue(1.0d);


            }

        });

        textMessagePane.setId("yourMessage");
        sp.setId("scroll-pane");
        chatPane.setId("chat");
        sendMessage.setId("sendMessage");
        sendMessage.setId("myMessagePane");
        myMessagePane.setId("myMessagePane");
        sendButtonMessage.setId("btnSendMessage");

        return chatPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
