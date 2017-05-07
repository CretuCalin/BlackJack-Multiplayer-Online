package networking;

import gamelogic.PlayerBehaviour;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by calin on 21.03.2017.
 */
public class PlayerCommunication extends PlayerBehaviour implements Runnable {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private Server server;


    private String message;

    private int turn;

    private volatile boolean finished;

    public PlayerCommunication(Socket socket, Server server, int turn,String name) {

        super(name);
        this.socket = socket;
        this.server = server;
        this.turn = turn;
        this.finished = false;
        //this.busted = false;
    }



    @Override
    public void run() {

        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();

            while(true)
            {
                if(server.getTurn() == this.turn && server.isGameStarted() && !finished)
                {
                    sendToClient("Enter option: HIT/STAND");
                    message = (String) input.readObject();
                    System.out.println(message);
                    server.getGame().interpretMessage(this, message);
                }
                if(server.isGameOver())
                {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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




}
