package pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by calin on 21.03.2017.
 */
public class Deck {

    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();

        for (int i = 2 ; i <= 14 ; i++){
            for ( Suit s : Suit.values()){
                cards.add(new Card(s.toString(),i));
            }
        }
        shuffle();
    }

    private void shuffle(){
        Collections.shuffle(cards);
    }

    public String toString(){
        return String.format("in pachet sunt %s de carti", cards.size());
    }

    public Card drawCard()
    {
        Card temp = cards.get(0);
        cards.remove(0);
        return temp;
    }



}
