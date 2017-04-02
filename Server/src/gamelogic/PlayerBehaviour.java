package gamelogic;

import pojo.Card;

import java.util.ArrayList;

/**
 * Created by calin on 21.03.2017.
 */
public class PlayerBehaviour{

    private ArrayList<Card> notAces; //the cards of each player
    private ArrayList<Card> aces;
    private ArrayList<Card> cards;
    private int total;
    private String name;

    public PlayerBehaviour(String name)
    {
        cards = new ArrayList<Card>();
        aces = new ArrayList<Card>();
        notAces = new ArrayList<Card>();
        total = 0;
        this.name = name;
    }

    public void setTotal()
    {
        total = 0;
        for(Card c : notAces)
        {
            if(c.getValue() >= 10)
                total += 10;
            else
                total += c.getValue();
        }
        for(Card a : aces)
        {
            if(total <= 10)
                total += 11;
            else
                total += 1;
        }
    }

    public void addCard(Card card)
    {
        if(card.getValue() == 11)
        {
            aces.add(card);
        }
        else
        {
            notAces.add(card);
        }
        cards.add(card);
        setTotal();

    }

    public String toString() {
        if (cards.size() != 0) {
            String result = String.format("The cards of %s are: \n", name);
            for (Card y : cards)
                result += y + "\n";
            result += String.format("with a total score of %d", total);
            return result;
        }
        else return "you are empty-handed";
    }



}
