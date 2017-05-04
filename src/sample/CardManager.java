package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Rares on 4/15/2017.
 */
public class CardManager extends AnchorPane{

    double left;
    double right;
    double top;
    double bottom;

    Double numberOfCardsInHand;

    public CardManager(double left, double right, double top, double bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        numberOfCardsInHand = 0.0;
    }

    public void addCard(String cardAddress){
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/profile_picture.jpg"),100,100,true,true));
        image.setFitHeight(140);
        image.setFitWidth(96.35);

        AnchorPane.setTopAnchor(image, 0.0);
        AnchorPane.setRightAnchor(image, numberOfCardsInHand * 19);
        AnchorPane.setLeftAnchor(image, 0.0);
        AnchorPane.setBottomAnchor(image, 0.0);

        numberOfCardsInHand++;
        this.getChildren().add(image);
    }
}
