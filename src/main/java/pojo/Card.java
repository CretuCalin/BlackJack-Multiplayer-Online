package pojo;

import java.io.Serializable;

/**
 * Created by calin on 21.03.2017.
 */
public class Card implements Serializable, Comparable<Card>{

    public static final long serialVerionUID = 1L;

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

    private int value;
    private String suit;

    public Card(String type, int value){
        this.value = value;
        this.suit = type;
    }


    public String toString()
    {
        String result = "";
        if(value == 11)
        {
            result = "Ace of " + suit;
            return result;
        }
        if(value == 12)
        {
            result = "Jack of " + suit;
            return result;
        }
        if(value == 13)
        {
            result = "Queen of " + suit;
            return result;
        }
        if(value == 14)
        {
            result = "King of " + suit;
            return result;
        }
        if(value <= 10)
        {
            result = value + " of " + suit;
            return result;
        }
        return value + " of " + suit;
    }


    @Override
    public int compareTo(Card o) {
        if (this.value == o.value && this.suit == o.suit)
            return 1;
        return 0;
    }
}
