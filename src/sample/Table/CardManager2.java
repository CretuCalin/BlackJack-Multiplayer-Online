package sample.Table;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karla on 30.05.2017.
 */
public class CardManager2 extends JLayeredPane {

    int numberOfCardsInHand;

    CardManager2(){
        setPreferredSize(new Dimension(200,137));
        numberOfCardsInHand = 0;
        //addCard("2 of Hearts.png");

    }

    public void addCard(String cardAddress){

        cardAddress = "src/resources/deck/" + cardAddress + ".png";
        System.out.println(cardAddress);




        JLabel imageLabel1 = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(cardAddress).getImage().getScaledInstance(100, 137, java.awt.Image.SCALE_DEFAULT));
        imageLabel1.setIcon(imageIcon);

        Insets insets = getInsets();
        Dimension size = imageLabel1.getPreferredSize();

        imageLabel1.setBounds(numberOfCardsInHand * 20 + insets.left, insets.top, size.width, size.height);
        numberOfCardsInHand = numberOfCardsInHand + 1;

        add(imageLabel1,new Integer(numberOfCardsInHand+1));

    }

}
