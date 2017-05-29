package networking;

import gamelogic.GameLogic;
import gamelogic.PlayerBehaviour;
import javafx.scene.control.Tab;
import login.Database;
import managers.DatabaseManager;
import managers.Manager;
import pojo.Table;
import pojo.User;

import javax.sql.DataSource;
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

    public User getUser() {
        return user;
    }

    private User user;

    private GameLogic game;
    private GameInstance gameInstance;

    private String message;

    private int turn;
    private volatile boolean finished;

    public void setTurn(int turn) {
        this.turn = turn;
    }

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
            System.out.println(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            finished = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean readBool(){
        try {
            boolean message = (boolean) input.readObject();
            System.out.println(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            finished = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verify(){
        try {
            user.setUsername(read());
            user.setPassword (read());
            String message = Manager.getInstance().getLoginManager().newPlayer(user);
            sendToClient(message);
            if (message.equals("User Dosen't exist") || message.equals("Wrong Password") || message.equals("An error has occurd"))
                return false;
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void enterATable() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        if(!finished)
        {
            message = read();
            System.out.println(message);
            if(MessagesInterpreter.getInstance().wantsToJoinTable(message)){
                String table = read();
                Table tab = Table.existingTable(table);
                user.setTable(tab);
                DatabaseManager.getInstance().addToTable(tab, user);
            }
            else if(MessagesInterpreter.getInstance().wantsToCreateTable(message)) {
                String table = read();
                Table tab = createTable(table, this);
                user.setTable(tab);
                user.setAdmin(true);
                tab.addPlayer(this);
                Table.addTable(tab);
            }
        }
    }

    public Table createTable(String name, PlayerCommunication player){
        int n = Integer.parseInt(read());
        boolean custom = readBool();
        String password = read();
        Table table = new Table(n, name, player, custom, password);
        DatabaseManager.getInstance().addNewTable(table);
        sendToClient(table.getID());
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

        while (!isFinished()){
            if(verify())
                break;
            System.out.println("Login failed");
        }
        try {
            sendToClient(Manager.getInstance().getTablesManager().printTables());
            enterATable();

            if(user.isAdmin()) {
                String message = read();
                if(message.equals("START GAME")) {
                    gameInstance = new GameInstance(user.getTable().getPlayers(), user.getTable().getNumberPlayers());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            game = new GameLogic(gameInstance);
                            game.startGame();
                            user.setTable(null);
                        }
                    }).start();
                }
            }
            System.out.println(gameInstance.isGameStarted());

            while (isOnTable() && !isFinished()) {
                //System.out.println("Asteeeept");
                if ((gameInstance.getTurn()+1) == this.turn && gameInstance.isGameStarted()  && !finished) {
                    System.out.println("Am asteeeeptat");
                    message = (String) input.readObject();
                    System.out.println(message);
                    game.interpretMessage(this, message);
                }
                if (!isOnTable()) {
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
            System.out.println(object + " - sent");
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
