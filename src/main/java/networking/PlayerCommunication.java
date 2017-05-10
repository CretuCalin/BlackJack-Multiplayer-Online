package networking;

import gamelogic.PlayerBehaviour;
import managers.Manager;
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


    private String message;
    private int turn;
    private volatile boolean finished;
    private String table;


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

    public boolean verify(){
        try {
            sendToClient("Username:" );
            user.setUsername((String) input.readObject());
            sendToClient("Password");
            user.setPassword ((String) input.readObject());
            String message = Manager.getInstance().getLoginManager().newPlayer(user);
            sendToClient(message);
            if (message.equals("User Dosen't exist.") || message.equals("Wrong Password.") || message.equals("An error has occurd."))
                return false;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void enterATable() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        while(!socket.isClosed())
        {
            sendToClient("Choose a table from:");
            Manager.getInstance().getTablesManager().printTables();
            message = (String) input.readObject();
            System.out.println(message);
            table = MessagesInterpreter.getInstance().tableInterpreter(message);
            user.setTable(table);
        }
    }

    public boolean isOnTable(){
        if(table != null){
            return true;
        }
        return false;
    }

    @Override
    public void run() {

        try {
            // waiting to enter a table
            enterATable();

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
           // e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
        } finally {
            user.close();
        }

    }

    public void sendToClient(Object object){
        try {
            output.writeObject(object);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void close() throws IOException {  user.close();   }


}
