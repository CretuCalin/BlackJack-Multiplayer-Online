package gamelogic;

import networking.GameInstance;
import networking.PlayerCommunication;
import pojo.Card;
import pojo.Deck;


public class GameLogic {

    private Deck deck;
    private GameInstance gameInstance;
    public boolean started = false;

    private PlayerBehaviour dealer;


    public GameLogic(GameInstance gameInstance)
    {
        deck = new Deck();
        this.gameInstance = gameInstance;
        started = true;
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
            System.out.println("Your turn is ");
            gameInstance.getThreads().get(i).sendToClient(i + 1);
            gameInstance.getThreads().get(i).setTurn(i+1);
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
            System.out.println("Players on the table:");
            gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().size());
            for(int j = 0; j < gameInstance.getNumberOfPlayers(); j++)
            {
                gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().get(j).getUser().getUsername());
                gameInstance.getThreads().get(i).sendToClient(i+1);
                gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().get(j).getCards().get(0));
                gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().get(j).getCards().get(1));
                gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().get(j).getTotal());
            }

        }

        for(int i = 0 ; i < gameInstance.getNumberOfPlayers(); i++)
        {

            gameInstance.getThreads().get(i).sendToClient("Dealer");
            gameInstance.getThreads().get(i).sendToClient(dealer.getCards().get(0));
        }

    }

    public void notifyPlayers(){
        for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++) {
            gameInstance.getThreads().get(i).sendToClient(gameInstance.getTurn()+1);
            gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().get(gameInstance.getTurn()).getUser().getUsername());
        }
    }

    public void playersTurn(){

        notifyPlayers();
        while(gameInstance.getTurn() < gameInstance.getNumberOfPlayers())
        {
            if(gameInstance.getThreads().get(gameInstance.getTurn()).isFinished())
            {
                gameInstance.setTurn(gameInstance.getTurn() + 1);
                if (gameInstance.getTurn() < gameInstance.getNumberOfPlayers())
                    notifyPlayers();
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

        for(int i = 0; i<gameInstance.getNumberOfPlayers(); i++)
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

        else if (message.equals("HIT"))
        {
            hitCardAndCheckBust(player);
        }
    }

    public void hitCardAndCheckBust(PlayerCommunication player)
    {
        System.out.println("Dau carte");
        Card card = deck.drawCard();
        player.addCard(card);

        for (int i = 0; i < gameInstance.getNumberOfPlayers(); i++)
        {
            gameInstance.getThreads().get(i).sendToClient(card);
            gameInstance.getThreads().get(i).sendToClient(gameInstance.getThreads().get(i).getTotal());
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
