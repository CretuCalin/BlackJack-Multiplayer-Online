package pojo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bobby on 29-05-2017.
 */
class DeckTest {
    @Test
    void drawCard() {

        Deck deck = new Deck();
        int size = 52;
        deck.toString();
        while (size>0){
            assertEquals(String.format("in pachet sunt %s de carti",size),deck.toString());
            deck.drawCard();
            size--;
        }

    }

    @Test
    void checkCards(){
        ArrayList<Card> cards = new ArrayList<>();
        Deck deck = new Deck();

        while (deck.getCards().size()>0){
            System.out.println(deck.getCards().size());
            Card temp = deck.drawCard();
            assertEquals(false,exists(cards,temp));
            cards.add(temp);
        }

    }

    boolean exists(ArrayList<Card> cards, Card card){
        for (Card c : cards)
            if (c.compareTo(card)==1)
                return true;
        return false;
    }

    @Test
    void checkShuffle(){
        for (int i=0;i<500;i++){
            Deck deck1 = new Deck();
            Deck deck2 = new Deck();
            assertEquals(false,deck1.getCards().equals(deck2.getCards()));
        }
    }

}