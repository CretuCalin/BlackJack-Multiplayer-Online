package sample;

import java.util.ArrayList;

/**
 * Created by teo on 25.03.2017.
 */
public class RulesBlackjack {
    static int count = 0;
    ArrayList<String> rules ;


    RulesBlackjack(){
        ArrayList<String> rules = new ArrayList<String>();
        rules.add("Blackjack, also known as 21, is the most popular casino-banked game in the world. The objective when playing blackjack is to get as close to 21 as you can with any number of cards.");
        rules.add("The game begins with the player(s) placing a bet in his/her allocate bet box.  The dealer will then deal all participating players 2 cards (both facing up) as well as 2 cards to him/her self (only 1 facing up). ");
        rules.add("The value of cards is as follows:" + "\n" + "*Cards 2-10 represent their own numerical value.\n" + "*Jacks, Queens, and Kings are worth 10.\n" + "*Aces are worth either 1 or 11, whichever the player chooses.");
        rules.add("If the player gets a \"natural\" or \"blackjack\", which is an Ace and 10-value card on the first two cards, then the player automatically wins their hand and is paid 3-to-2 odds on their bet, unless the dealer also has a blackjack (in which case the hand is a tie).");
        rules.add("If the dealer's upcard is an Ace, then he will check to see if he has a blackjack. If the dealer has a blackjack, all players not holding a blackjack lose.\nEach player must decide what to do by exercising one of their four options: hit, stand, double down, or split.");
        rules.add("*Stay – This means that you do not want any more cards.\n*Hit – This means you want another card, you can take as many cards as you want unless you bust.");
        rules.add("*Split – This is an option to split your one hand into two separate hands. You can only use this option if both of your cards are the same (i.e. two 7's or two Aces).You cannot go to the second hand until you have finished playing the first hand.\n");
        rules.add("*Double Down – This allows you to double your original bet after you have received your two cards. If you double down, you are only allowed only one more card.\nIf the player goes over 21, the player \"busts\" and loses.");
        rules.add("It is then the dealer's turn to play their hand. The dealer must play according to predetermined rules. If the dealer has less than 17, he must hit. If the dealer has 17 or more, he must stand, unless their hand is a \"soft 17\" (a hand that includes an ace valued as \"11\").");
        rules.add("If the dealer busts, then all of the remaining players win the hand. If the dealer doesn't bust, then the remaining players must have a higher hand than the dealer in order to have a winning hand. If a player ties the dealer then it is a \"push\" and the player keeps his bet.");
        this.rules = rules;
    }


    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getCount(){
        return count;
    }

    public String getRule(int i){
        return rules.get(i);
    }
}
