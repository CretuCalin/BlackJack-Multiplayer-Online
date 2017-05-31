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
import java.lang.reflect.Array;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by calin on 21.03.2017.
 */
public class PlayerCommunication extends PlayerBehaviour  {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private Server server;
    private boolean gameStarted ;

    public User getUser() {
        return user;
    }

    private User user;
    private String message;

    private int turn;
    private volatile boolean finished;
    public boolean ended;

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
        this.gameStarted = false;
        ended = false;
        setUpStreams();
    }

    public void setUpStreams(){
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            user.setInputStream(input);
            user.setOutputStream(output);
            System.out.println("Stream seted");
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
                tab.addPlayer(this);

            }
            else if(MessagesInterpreter.getInstance().wantsToCreateTable(message)) {
                String table = read();
                Table tab = createTable(table, this);
                user.setTable(tab);
                user.setAdmin(true);
                tab.addPlayer(this);
                DatabaseManager.getInstance().addToTable(tab, user);
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
    public void run() {

        while (!isFinished()){
            if(verify())
                break;
            System.out.println("Login failed");
        }
        try {
            //while (true){
                sendToClient(Manager.getInstance().getTablesManager().printTables());
                enterATable();

                if(user.isAdmin()) {
                    String message = read();
                    if(message.equals("START GAME")) {
                        user.getTable().setGameInstance(new GameInstance(user.getTable().getPlayers(), user.getTable().getNumberPlayers()));
                        user.getTable().setGame(new GameLogic(user.getTable().getGameInstance()));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                for(int i = 0; i<user.getTable().getNumberPlayers(); i++){
                                    System.out.println("Am trimis la " + i);
                                    user.getTable().getPlayers().get(i).gameStarted = true;
                                    if(user.getTable().getPlayers().get(i).getUser() != user){
                                        user.getTable().getPlayers().get(i).sendToClient("GAME STARTED");
                                    }

                                }
                                user.getTable().getGame().startGame();
                                ArrayList<PlayerCommunication> players = user.getTable().getPlayers();
                                int ID = user.getTable().getID();
                                for (int i = 0; i < players.size() ; i++){
                                    players.get(i).getUser().setTable(null);
                                }
                                DatabaseManager.getInstance().deleteUsers(players);
                                DatabaseManager.getInstance().deleteTable(ID);
                            }
                        }).start();
                        while(!user.getTable().getGameInstance().isGameStarted()){}
                    }
                }else{
                    while(user.getTable().getGameInstance() == null){}
                    while(!user.getTable().getGameInstance().isGameStarted()){}
                    System.out.println("Clientul a trecut mai departe " + user.getTable().getGameInstance().isGameStarted());
                }

                while (isOnTable() && user.getTable().getGameInstance().isGameStarted()) {
                    if ((user.getTable().getGameInstance().getTurn()+1) == this.turn && user.getTable().getGameInstance().isGameStarted()  && !finished) {
                        System.out.println("Am asteeeeptat");
                        message = (String) input.readObject();
                        user.getTable().getGame().interpretMessage(this, message);
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
