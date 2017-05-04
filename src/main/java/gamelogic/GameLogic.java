package gamelogic;

import networking.PlayerCommunication;
import networking.Server;
import pojo.Card;
import pojo.Deck;


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

        for(int i = 0; i < server.getNumberOfPlayers(); i++)
        {
            server.getThreads().get(i).sendToClient("Your turn is " + (i + 1));
        }

    }

    public void setTwoCards(){

        Card card;
        for(int i = 0; i < server.getNumberOfPlayers(); i++)
        {
            card = deck.drawCard();
            server.getThreads().get(i).addCard(card);
            card = deck.drawCard();
            server.getThreads().get(i).addCard(card);
        }
        card = deck.drawCard();
        dealer.addCard(card);
        card = deck.drawCard();
        dealer.addCard(card);

    }

    public void showCards(){

        for(int i = 0; i < server.getNumberOfPlayers(); i++)
        {
            for(int j = 0; j < server.getNumberOfPlayers(); j++)
            {
                server.getThreads().get(j).sendToClient("Player " + (i + 1));
                server.getThreads().get(j).sendToClient(server.getThreads().get(i).getCards().get(0));
                server.getThreads().get(j).sendToClient(server.getThreads().get(i).getCards().get(1));
                server.getThreads().get(j).sendToClient(server.getThreads().get(i).getTotal());
            }

        }

        for(int i = 0 ; i < server.getNumberOfPlayers(); i++)
        {
            server.getThreads().get(i).sendToClient("Dealer");
            server.getThreads().get(i).sendToClient(dealer.getCards().get(0));
        }

    }

    public void playersTurn(){

        for (int i = 0; i < server.getNumberOfPlayers(); i++)
            server.getThreads().get(i).sendToClient("Player " + (server.getTurn() + 1));
        while(server.getTurn() < server.getNumberOfPlayers())
        {
            if(server.getThreads().get(server.getTurn()).isFinished())
            {
                server.setTurn(server.getTurn() + 1);
                if (server.getTurn() < server.getNumberOfPlayers())
                    for (int i = 0; i < server.getNumberOfPlayers(); i++)
                        server.getThreads().get(i).sendToClient("Player " + (server.getTurn() + 1));
            }
        }

    }

    public void dealersTurn(){

        for(int i = 0; i < server.getNumberOfPlayers(); i++)
        {
            server.getThreads().get(i).sendToClient("Dealer");
            server.getThreads().get(i).sendToClient(dealer.getCards().get(1));
        }
        Card card;

        while(dealer.getTotal() < getMinimum())
        {
            card = deck.drawCard();
            dealer.addCard(card);
            for(int i = 0; i < server.getNumberOfPlayers(); i++)
            {
                server.getThreads().get(i).sendToClient(card);
            }
        }

    }

    public int getMinimum(){

        int minimum = 22;
        for(int i = 0;i < server.getNumberOfPlayers(); i++)
            if(minimum > server.getThreads().get(i).getTotal())
                minimum = server.getThreads().get(i).getTotal();
        return minimum;
    }

    public void sendResoults(){

        for(int i = 0; i<server.getThreads().size(); i++)
        {
            if(server.getThreads().get(i).getTotal() <= 21)
            {
                if(dealer.getTotal() <= 21)
                {
                    if(dealer.getTotal() > server.getThreads().get(i).getTotal())
                    {
                        server.getThreads().get(i).sendToClient("Player " + (i + 1));
                        server.getThreads().get(i).sendToClient("LOSE");
                    }
                    else if (dealer.getTotal() == server.getThreads().get(i).getTotal())
                    {
                        server.getThreads().get(i).sendToClient("Player " + (i + 1));
                        server.getThreads().get(i).sendToClient("DRAW");
                    }
                    else
                    {
                        server.getThreads().get(i).sendToClient("Player " + (i + 1));
                        server.getThreads().get(i).sendToClient("WIN");
                    }
                }
                else {
                    server.getThreads().get(i).sendToClient("Player " + (i + 1));
                    server.getThreads().get(i).sendToClient("Dealer Busted! You Win");
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

        for (int i = 0; i < server.getNumberOfPlayers(); i++)
        {
            server.getThreads().get(i).sendToClient(card);
        }
        if(player.getTotal() > 21)
        {
            player.setFinished(true);
            for (int i = 0; i < server.getNumberOfPlayers(); i++)
            {
                server.getThreads().get(i).sendToClient("BUST");
            }

        }
        else
        {
            for (int i = 0; i < server.getNumberOfPlayers(); i++)
            {
                server.getThreads().get(i).sendToClient(player.getTotal());
            }

        }
    }



}
