package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by calin on 21.03.2017.
 */
public class PlayerCommunication implements Runnable {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private Server server;

    private String message;


    public PlayerCommunication(Socket socket, Server server, String name){
        this.socket = socket;
        this.server = server;
    }


    @Override
    public void run() {

        try{
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToClient(Object object){
        try{
            output.writeObject(object);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
