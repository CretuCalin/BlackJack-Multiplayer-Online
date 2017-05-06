package gamelogic;

import networking.GameInstance;
import networking.PlayerCommunication;
import pojo.Card;
import pojo.Deck;


public class GameLogic {

    private Deck deck;
    private GameInstance gameInstance;

    private PlayerBehaviour dealer;

    public GameLogic(GameInstance gameInstance)
    {
        deck = new Deck();
        this.gameInstance = gameInstance;
        dealer = new PlayerBehaviour("dealer");
    }

    public void startGame(){



    }

    private void setPlayersOrder()
    {

        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads().get(i).sendToClient("Your turn is " + (i + 1));
        }

    }

    public void setTwoCards(){

        Card card;
        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            card = deck.drawCard();
            gameInstance.getThreads().get(i).addCard(card);
            card = deck.drawCard();
            gameInstance.getThreads().get(i).addCard(card);
        }
        card = deck.drawCard();
        dealer.addCard(card);
        card = deck.drawCard();
        dealer.addCard(card);

    }

    public void showCards(){

        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            for(int j = 0; j < gameInstance.getNumberOfPlayers(); j++)
            {
                gameInstance.getThreads().get(j).sendToClient("Player " + (i + 1));
                gameInstance.getThreads().get(j).sendToClient(gameInstance.getThreads().get(i).getCards().get(0));
                gameInstance.getThreads().get(j).sendToClient(gameInstance.getThreads().get(i).getCards().get(1));
                gameInstance.getThreads().get(j).sendToClient(gameInstance.getThreads().get(i).getTotal());
            }

        }

        for(int i = 0 ; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads().get(i).sendToClient("Dealer");
            gameInstance.getThreads().get(i).sendToClient(dealer.getCards().get(0));
        }

    }

    public void playersTurn(){

        for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            gameInstance.getThreads().get(i).sendToClient("Player " + (gameInstance.getTurn() + 1));
        while(gameInstance.getTurn() < gameInstance.getNumberOfPlayers())
        {
            if(gameInstance.getThreads().get(gameInstance.getTurn()).isFinished())
            {
                gameInstance.setTurn(gameInstance.getTurn() + 1);
                if (gameInstance.getTurn() < gameInstance.getNumberOfPlayers())
                    for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
                        gameInstance.getThreads().get(i).sendToClient("Player " + (gameInstance.getTurn() + 1));
            }
        }

    }

    public void dealersTurn(){

        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads().get(i).sendToClient("Dealer");
            gameInstance.getThreads().get(i).sendToClient(dealer.getCards().get(1));
        }
        Card card;

        while(dealer.getTotal() < getMinimum())
        {
            card = deck.drawCard();
            dealer.addCard(card);
            for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            {
                gameInstance.getThreads().get(i).sendToClient(card);
            }
        }

    }

    public int getMinimum(){

        int minimum = 22;
        for(int i = 0;i < gameInstance.getNumberOfPlayers(); i++)
            if(minimum > gameInstance.getThreads().get(i).getTotal())
                minimum = gameInstance.getThreads().get(i).getTotal();
        return minimum;
    }

    public void sendResoults(){

        for(int i = 0; i<gameInstance.getThreads().size(); i++)
        {
            if(gameInstance.getThreads().get(i).getTotal() <= 21)
            {
                if(dealer.getTotal() <= 21)
                {
                    if(dealer.getTotal() > gameInstance.getThreads().get(i).getTotal())
                    {
                        gameInstance.getThreads().get(i).sendToClient("Player " + (i + 1));
                        gameInstance.getThreads().get(i).sendToClient("LOSE");
                    }
                    else if (dealer.getTotal() == gameInstance.getThreads().get(i).getTotal())
                    {
                        gameInstance.getThreads().get(i).sendToClient("Player " + (i + 1));
                        gameInstance.getThreads().get(i).sendToClient("DRAW");
                    }
                    else
                    {
                        gameInstance.getThreads().get(i).sendToClient("Player " + (i + 1));
                        gameInstance.getThreads().get(i).sendToClient("WIN");
                    }
                }
                else {
                    gameInstance.getThreads().get(i).sendToClient("Player " + (i + 1));
                    gameInstance.getThreads().get(i).sendToClient("Dealer Busted! You Win");
                }
            }
        }

    }

    public void interpretMessage(PlayerCommunication player, String message)
    {
        if (message.equals("STAND"))
        {
            player.setFinished(true);
        }

        if (message.equals("HIT"))
        {
            hitCardAndCheckBust(player);
        }
    }

    public void hitCardAndCheckBust(PlayerCommunication player)
    {
        Card card = deck.drawCard();
        player.addCard(card);

        for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads().get(i).sendToClient(card);
        }
        if(player.getTotal() > 21)
        {
            player.setFinished(true);
            for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            {
                gameInstance.getThreads().get(i).sendToClient("BUST");
            }

        }
        else
        {
            for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            {
                gameInstance.getThreads().get(i).sendToClient(player.getTotal());
            }

        }
    }



}
