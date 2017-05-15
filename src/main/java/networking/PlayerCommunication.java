package networking;

import gamelogic.GameLogic;
import gamelogic.PlayerBehaviour;
import javafx.scene.control.Tab;
import managers.Manager;
import pojo.Table;
import pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

/**
 * Created by calin on 21.03.2017.
 */
public class PlayerCommunication extends PlayerBehaviour implements Runnable {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private Server server;
    private User user;

    private GameLogic game;

    private String message;
    private int turn;
    private volatile boolean finished;


    public PlayerCommunication(User user, Server server, int turn, String name) {

        super(name);
        this.user = user;
        this.socket = user.getSocket();
        this.server = server;
        this.turn = turn;
        this.finished = false;
        setUpStreams();
    }

    public void setUpStreams(){
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            user.setInputStream(input);
            user.setOutputStream(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String read(){
        try {
            String message = (String) input.readObject();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            finished = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verify(){
        try {
            sendToClient("Username:" );
            user.setUsername(read());
            sendToClient("Password");
            user.setPassword (read());
            String message = Manager.getInstance().getLoginManager().newPlayer(user);
            sendToClient(message);
            if (message.equals("User Dosen't exist.") || message.equals("Wrong Password.") || message.equals("An error has occurd."))
                return false;
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void enterATable() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        String table;
        if(!finished)
        {
            sendToClient("Choose a table from:");
            Manager.getInstance().getTablesManager().printTables();
            message = read();
            System.out.println(message);
            table = MessagesInterpreter.getInstance().tableInterpreter(message);
            Table tab = Table.existingTable(table);
            if(tab != null){
                user.setTable(tab);
                tab.addPlayer(this);
            }
            else {
                tab = createTable(table, this);
                user.setTable(tab);
                user.setAdmin(true);
                tab.addPlayer(this);
                Table.addTable(tab);
            }
        }
    }

    public Table createTable(String name, PlayerCommunication player){
        sendToClient("Numer of players");
        int n = Integer.parseInt(read());
        Table table = new Table(n, name, player);
        sendToClient("Table createad");
        System.out.println("Table createad " + table.getName());
        return table;
    }

    public boolean isOnTable(){
        if(user.getTable() != null){
            return true;
        }
        return false;
    }

    @Override
    public void run() {

        try {
            // waiting to enter a table
            enterATable();

            if(user.isAdmin()) {
                sendToClient("Do we start the game?");
                String message = read();
                if(message.equals("NOW"));
                GameInstance gameInstance = new GameInstance(user.getTable().getPlayers(), user.getTable().getNumberPlayers());
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        game = new GameLogic(gameInstance);
                        game.startGame();

                    }
                }).start();
            }

            while (isOnTable()) {
                if (server.getTurn() == this.turn && server.isGameStarted() && !finished) {
                    sendToClient("Enter option: HIT/STAND");
                    message = (String) input.readObject();
                    System.out.println(message);
                    server.getGame().interpretMessage(this, message);
                }
                if (server.isGameOver()) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public void sendToClient(Object object){
        try {
            output.writeObject(object);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void close()  {  user.close();   }


}
