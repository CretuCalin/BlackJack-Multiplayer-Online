package pojo;

import java.io.Serializable;

/**
 * Created by calin on 21.03.2017.
 */
public class Card implements Serializable{

    public static final long serialVerionUID = 1L;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    private int points;
    private int value;
    private String suit;

    public Card(String type, int value){
        this.value = value;
        this.suit = suit;
        if(value < 12){
            this.points = value;
        }else{
            this.points = 10;
        }
    }


    public String toString()
    {
        String result = "";
        if(value == 11)
        {
            result = "ace_of_" + suit;
        }
        if(value == 12)
        {
            result = "jack_of_" + suit;
        }
        if(value == 13)
        {
            result = "queen_of_" + suit;
        }
        if(value == 14)
        {
            result = "king_of_" + suit;
        }
        if(value <= 10)
        {
            result = value + "_of_" + suit;
        }
        return result;
    }



}
