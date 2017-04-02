package gamelogic;

import networking.Server;
import pojo.Deck;

/**
 * Created by calin on 21.03.2017.
 */
public class GameLogic {

    private Deck deck;
    private Server server;

    private PlayerBehaviour dealer;

    public GameLogic(Server server)
    {
        deck = new Deck();
        this.server = server;
        dealer = new PlayerBehaviour("dealer");
    }

    public void startGame(){

    }

    private void setPlayersOrder()
    {

    }

    public void setTwoCards(){

    }

    public void showCards(){

    }

    public void playersTurn(){

    }

    public void dealersTurn(){

    }

    public int getMinimum(){
        return - 1;
    }

    public void sendResoults(){

    }


}
