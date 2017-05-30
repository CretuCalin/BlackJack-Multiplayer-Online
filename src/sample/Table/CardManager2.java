package sample.Table;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pojo.Card;

import javax.swing.*;

/**
 * Created by Karla on 30.05.2017.
 */
public class CardManager2 extends JLabel {

    double left;
    double right;
    double top;
    double bottom;
    String username = "";
    int total = 0;
    int id = -1;

    int numberOfCardsInHand;

    Text resultText;

    public CardManager2(){
        numberOfCardsInHand = 0;
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
