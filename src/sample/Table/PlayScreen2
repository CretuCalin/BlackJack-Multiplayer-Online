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
    private ArrayList<CardManager> playersCards = new ArrayList<CardManager>();
    private ArrayList<String> playerUsernames = new ArrayList<String>();

    private CardManager dealerCards = new CardManager();

    private int myId;
    private int nowPlayer;

    private JButton hitButton;
    private JButton standButton;

    public static void main(String[] args){
        jframe = new JFrame("Blackjack Elite");
        jframe.setPreferredSize(new Dimension(1386,768));
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel borderPane = new JPanel (new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel tablePanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10,10,10,10);

        for(int i = 0; i < 6; i++){
            CardManager playerCards = new CardManager();
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
                playersCards.get(2).addCard("5 of Spades.png");
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
