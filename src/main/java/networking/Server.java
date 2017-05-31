package networking;

import gamelogic.GameLogic;
import javafx.scene.control.Tab;
import managers.Manager;
import org.omg.CORBA.IRObjectOperations;
import pojo.Table;
import pojo.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by calin on 21.03.2017.
 */

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private ExecutorService threadPool;

    private ArrayList<PlayerCommunication> waiting;
    private ArrayList<PlayerCommunication> playing;

    public static int numberOfPlayersOfThisGame;
    private int totalNumberOfPlayers;
    private volatile boolean running;

    private final int portNumber = 9998;

    private GameLogic game;

    private final int numberOfPlayersPerGame = 4;
    private Server server;


    public Server(){

        threadPool = Executors.newCachedThreadPool();
        waiting = new ArrayList<>();
        playing = new ArrayList<>();
        numberOfPlayersOfThisGame = 0;
        totalNumberOfPlayers = 0;
        running = true;
        server = this;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is online");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void waitForConnections() {

        System.out.println("waiting for connections");
        while (running && totalNumberOfPlayers < 16)
        {

            try {
                clientSocket = serverSocket.accept();
                System.out.println("new client");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            User user = new User(clientSocket);
                            PlayerCommunication player = new PlayerCommunication(user, server, numberOfPlayersOfThisGame, "Player " + (numberOfPlayersOfThisGame + 1));
                            player.run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public ArrayList<PlayerCommunication> getWaiting() {
        return waiting;
    }

    public void addWaiting(PlayerCommunication player) {
        waiting.add(player);
    }

    public static Socket getClientSocket() {
        return clientSocket;
    }

    public static void setClientSocket(Socket clientSocket) {
        Server.clientSocket = clientSocket;
    }

    public int getNumberOfPlayersOfThisGame() {
        return numberOfPlayersOfThisGame;
    }

    public void setNumberOfPlayersOfThisGame(int numberOfPlayersOfThisGame) {
        this.numberOfPlayersOfThisGame = numberOfPlayersOfThisGame;
    }

    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    public void setTotalNumberOfPlayers(int totalNumberOfPlayers) {
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public GameLogic getGame() {
        return game;
    }

    public void setGame(GameLogic game) {
        this.game = game;
    }

    public int getNumberOfPlayersPerGame() {
        return numberOfPlayersPerGame;
    }
}
