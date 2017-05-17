package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.*;

/**
 * Created by Rares on 4/15/2017.
 */
public class CardManager extends AnchorPane{

    double left;
    double right;
    double top;
    double bottom;

    Double numberOfCardsInHand;

    Text result;

    public CardManager(double left, double right, double top, double bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        numberOfCardsInHand = 0.0;

        result = new Text("Playing...");
        result.setFont(Font.font("Veranda", 20));
        result.setId("myMessagePane");

        AnchorPane.setTopAnchor(result, 160.0);
        AnchorPane.setLeftAnchor(result, 55.0);
        AnchorPane.setBottomAnchor(result, 0.0);
        AnchorPane.setRightAnchor(result, 0.0);

        this.getChildren().add(result);
    }

    public void addCard(String cardAddress){
        if(numberOfCardsInHand < 5) {
            Image image = new Image("/resources/deck/2 of Clubs.png");
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

            if(numberOfCardsInHand == 4){
                result.setText("Hand Full");
            }
        }
        else{
            result.setText("Hand Full");
        }
    }

    public void setResult(String message){
        result.setText(message);
    }
}
