package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * Created by calin on 21.03.2017.
 */

public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private ExecutorService threadPool;

    private ArrayList<PlayerCommunication> threads;

    private final int portNumber = 9797;

    private int noOfPlayers;



    public Server(){

        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started");
            noOfPlayers = 0;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForConnections() {

        while(true){

            try{
                clientSocket = serverSocket.accept();
                System.out.println("Player connected");
                noOfPlayers ++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
