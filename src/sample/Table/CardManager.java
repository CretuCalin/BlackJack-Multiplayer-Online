package sample.Table;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.ConnectionController;
import pojo.Card;

/**
 * Created by Rares on 4/15/2017.
 */
public class CardManager extends AnchorPane{

    double left;
    double right;
    double top;
    double bottom;
    String username = "";
    int total = 0;
    int id = -1;

    Double numberOfCardsInHand;

    Text resultText;

    public CardManager(double left, double right, double top, double bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        numberOfCardsInHand = 0.0;

        resultText = new Text(username);
        resultText.setFont(Font.font("Veranda", 20));
        resultText.setId("myMessagePane");

        StackPane resultPane = new StackPane();
        resultPane.setAlignment(resultText, Pos.CENTER);
        resultPane.getChildren().add(resultText);
        //resultPane.setOpacity(0.5);
        //resultText.setOpacity(1.0);
        resultPane.setStyle("-fx-background-color: rgba(198, 159, 94, 0.75); -fx-background-radius: 10;");

        AnchorPane.setTopAnchor(resultPane, 160.0);
        AnchorPane.setLeftAnchor(resultPane, 0.0);
        AnchorPane.setBottomAnchor(resultPane, 0.0);
        AnchorPane.setRightAnchor(resultPane, 0.0);

        this.getChildren().add(resultPane);
    }

    public String getUsername(){
        return this.username;
    }

    public void setTotal(int x){
        total = x;
    }

    public void addCard(Card card){
        if(numberOfCardsInHand < 5) {
            System.out.println("/resources/deck/" + card.toString() + ".png");
            Image image = new Image("/resources/deck/" + card.toString() + ".png");
            ImageView iv1 = new ImageView();
            iv1.setFitHeight(145.20);
            iv1.setFitWidth(100);

            iv1.setImage(image);

            AnchorPane.setTopAnchor(iv1, 0.0);
            AnchorPane.setLeftAnchor(iv1, numberOfCardsInHand * 20.0);
            AnchorPane.setBottomAnchor(iv1, 0.0);
            AnchorPane.setRightAnchor(iv1, 0.0);

            numberOfCardsInHand = numberOfCardsInHand + 1;

            this.getChildren().add(iv1);

            if(numberOfCardsInHand == 5){
                resultText.setText("Hand Full");
            }
        }
        else{
            resultText.setText("Hand Full");
        }
    }

    public void setResult(String message){
        resultText.setText(message);
    }
}
