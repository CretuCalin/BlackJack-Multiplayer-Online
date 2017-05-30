package sample.Table;

import javafx.application.Platform;
import pojo.Card;
import sample.ConnectionController;
import sample.Table.CardManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by rares on 29-May-17.
 */
public class PlayScreen2 {

    private JFrame jframe;
    private ArrayList<CardManager2> playersCards = new ArrayList<CardManager2>();
    private ArrayList<String> playerUsernames = new ArrayList<String>();

    int myPlayerNumber = -1;
    int numberOfPlayers = 0;
    int currentPlayer = 0;

    private CardManager2 dealerCards = new CardManager2();

    private int myId;
    private int nowPlayer;

    private JButton hitButton;
    private JButton standButton;

    private static PlayScreen2 instance ;

    public static PlayScreen2 getInstance(){
        if(instance == null)
            instance = new PlayScreen2();
        return instance;
    }


    private PlayScreen2(){
        getPreliminaryInfo();
        start();
        play();
    }

    public void getPreliminaryInfo(){
        String message = ConnectionController.getInstance().getSomeText();
        myPlayerNumber = Character.getNumericValue(message.charAt(message.length() - 1));
        message = ConnectionController.getInstance().getSomeText();
        numberOfPlayers = Character.getNumericValue(message.charAt(message.length() - 1)) + 1;

        System.out.println(myPlayerNumber);
        System.out.println(numberOfPlayers);
    }

    public void play() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Object messageReceived = ConnectionController.getInstance().read();
                    if (messageReceived instanceof Card) {
                        //while (created) {}
                        playersCards.get(currentPlayer-1).addCard((Card) messageReceived);
                    }
                    else if (messageReceived instanceof Integer) {
                        playersCards.get(currentPlayer-1).setTotal((int) messageReceived);
                    }
                    else if (messageReceived instanceof String) {
                        String message = messageReceived.toString();
                        processString(message);
                    }
                }
            }

        });
        thread.start();

    }

    public void showButtons(){
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }

    private void processString(String message)
    {
        boolean optionTester = true;
        if(message.equals("Start") && optionTester == true){

            optionTester = false;
        }
        if((message.contains("Player") || message.equals("Dealer")) && optionTester == true)
        {
            if(message.contains("Player")){
                char lastChar = message.charAt(message.length() - 1);
                currentPlayer = Character.getNumericValue(lastChar);
            }
            else
                currentPlayer = numberOfPlayers;
            optionTester=false;
        }
        if(message.contains("Player turn") && optionTester == true){
            if(currentPlayer == numberOfPlayers)
            {
                showButtons();
            }
            optionTester = true;
        }

        if ((message.equals("You Win") || message.equals("You Lost") || message.equals("Draw")
                || message.equals("Dealer BUSTED! You Win!"))  && optionTester == true)
        {
            //gameFrame.updateInfoLabel(message);
            //gameFrame.hideButtons();
            //this.client.setRunning(false);
            optionTester=false;
        }
        if (message.equals("BUSTED") && optionTester == true)
        {
            //sendTotal("BUST");
            /*if(myTurn)
            {
                gameFrame.updateInfoLabel(message);
                gameFrame.hideButtons();
                myTurn = false;
                this.client.setRunning(false);
            }*/
            optionTester=false;
        }
        if (message.equals("Dealer's turn") && optionTester == true)
        {
            //sendCurrentPlayer("Dealer");
            optionTester=false;
        }
        if(message.contains("Your score is "))
        {
            System.out.println(message);
            //login.dispose();
            //gameFrame = new GameFrame(currentProcessor);
            //gameFrame.setVisible(true);
        }
        if(optionTester == true && !message.contains("Your score is "))
        {
            //client.sendMessage(message);
        }

    }

    public void start(){
        jframe = new JFrame("Blackjack Elite");
        jframe.setPreferredSize(new Dimension(1386,768));
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel borderPane = new JPanel (new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel tablePanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10,10,10,10);

        for(int i = 0; i < numberOfPlayers; i++){
            CardManager2 playerCards = new CardManager2();
            gbc.gridx = i;
            gbc.gridy = 2;
            tablePanel.add(playerCards,gbc);
            playersCards.add(playerCards);

            String user = "User " + i;
            gbc.gridx = i;
            gbc.gridy = 3;
            playerUsernames.add(user);
            tablePanel.add(new JLabel(user),gbc);
        }


        gbc.gridx = 2;
        gbc.gridy = 0;
        tablePanel.add(dealerCards,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        tablePanel.add(new JLabel("Dealer"),gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        tablePanel.add(new JLabel("Turn:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        tablePanel.add(new JLabel("User" + nowPlayer), gbc);


        hitButton = new JButton("HIT");
        hitButton.setEnabled(false);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Trimit la Server Hit");
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 5;
        tablePanel.add(hitButton, gbc);

        standButton = new JButton("STAND");
        standButton.setEnabled(false);
        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Trimit la Server Stand");
            }
        });
        gbc.gridx = 3;
        gbc.gridy = 5;
        tablePanel.add(standButton, gbc);

        borderPane.add(tablePanel, BorderLayout.CENTER);

        jframe.add(borderPane);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

    public void setNowPlayer(int id){
        nowPlayer = id;
        if(myId == id){
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
        }

    }
}
