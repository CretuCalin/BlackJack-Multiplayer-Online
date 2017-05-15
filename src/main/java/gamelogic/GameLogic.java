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

        setPlayersOrder();
        setTwoCards();
        showCards();

        gameInstance.setGameStarted(true);

        playersTurn();
        dealersTurn();
        sendResoults();

        gameInstance.setGameOver(true);


    }

    private void setPlayersOrder()
    {

        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads()[i].sendToClient("Your turn is " + (i + 1));
        }

    }

    public void setTwoCards(){

        Card card;
        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            card = deck.drawCard();
            gameInstance.getThreads()[i].addCard(card);
            card = deck.drawCard();
            gameInstance.getThreads()[i].addCard(card);
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
                gameInstance.getThreads()[j].sendToClient("Player " + (i + 1));
                gameInstance.getThreads()[j].sendToClient(gameInstance.getThreads()[i].getCards().get(0));
                gameInstance.getThreads()[j].sendToClient(gameInstance.getThreads()[i].getCards().get(1));
                gameInstance.getThreads()[j].sendToClient(gameInstance.getThreads()[i].getTotal());
            }

        }

        for(int i = 0 ; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads()[i].sendToClient("Dealer");
            gameInstance.getThreads()[i].sendToClient(dealer.getCards().get(0));
        }

    }

    public void playersTurn(){

        for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            gameInstance.getThreads()[i].sendToClient("Player " + (gameInstance.getTurn() + 1));
        while(gameInstance.getTurn() < gameInstance.getNumberOfPlayers())
        {
            if(gameInstance.getThreads()[gameInstance.getTurn()].isFinished())
            {
                gameInstance.setTurn(gameInstance.getTurn() + 1);
                if (gameInstance.getTurn() < gameInstance.getNumberOfPlayers())
                    for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
                        gameInstance.getThreads()[i].sendToClient("Player " + (gameInstance.getTurn() + 1));
            }
        }

    }

    public void dealersTurn(){

        for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads()[i].sendToClient("Dealer");
            gameInstance.getThreads()[i].sendToClient(dealer.getCards().get(1));
        }
        Card card;

        while(dealer.getTotal() < getMinimum())
        {
            card = deck.drawCard();
            dealer.addCard(card);
            for(int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            {
                gameInstance.getThreads()[i].sendToClient(card);
            }
        }

    }

    public int getMinimum(){

        int minimum = 22;
        for(int i = 0;i < gameInstance.getNumberOfPlayers(); i++)
            if(minimum > gameInstance.getThreads()[i].getTotal())
                minimum = gameInstance.getThreads()[i].getTotal();
        return minimum;
    }

    public void sendResoults(){

        for(int i = 0; i<gameInstance.getNumberOfPlayers(); i++)
        {
            if(gameInstance.getThreads()[i].getTotal() <= 21)
            {
                if(dealer.getTotal() <= 21)
                {
                    if(dealer.getTotal() > gameInstance.getThreads()[i].getTotal())
                    {
                        gameInstance.getThreads()[i].sendToClient("Player " + (i + 1));
                        gameInstance.getThreads()[i].sendToClient("LOSE");
                    }
                    else if (dealer.getTotal() == gameInstance.getThreads()[i].getTotal())
                    {
                        gameInstance.getThreads()[i].sendToClient("Player " + (i + 1));
                        gameInstance.getThreads()[i].sendToClient("DRAW");
                    }
                    else
                    {
                        gameInstance.getThreads()[i].sendToClient("Player " + (i + 1));
                        gameInstance.getThreads()[i].sendToClient("WIN");
                    }
                }
                else {
                    gameInstance.getThreads()[i].sendToClient("Player " + (i + 1));
                    gameInstance.getThreads()[i].sendToClient("Dealer Busted! You Win");
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
            gameInstance.getThreads()[i].sendToClient(card);
        }
        if(player.getTotal() > 21)
        {
            player.setFinished(true);
            for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            {
                gameInstance.getThreads()[i].sendToClient("BUST");
            }

        }
        else
        {
            for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
            {
                gameInstance.getThreads()[i].sendToClient(player.getTotal());
            }

        }
    }





}
