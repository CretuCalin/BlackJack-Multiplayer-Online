package networking;

import gamelogic.GameLogic;
import managers.Manager;
import org.omg.CORBA.IRObjectOperations;
import pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

    private int numberOfPlayersOfThisGame;
    private int totalNumberOfPlayers;
    private volatile boolean running;

    private final int portNumber = 9998;

    private volatile int turn;

    private volatile boolean gameStarted;
    private volatile boolean gameOver;

    private GameLogic game;
    private GameInstance gameInstance ;

    private final int numberOfPlayersPerGame = 4;


    public Server(){

        threadPool = Executors.newCachedThreadPool();
        waiting = new ArrayList<>();
        playing = new ArrayList<>();
        numberOfPlayersOfThisGame = 0;
        totalNumberOfPlayers = 0;
        turn = 0;
        gameStarted = false;
        running = true;

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

                User user = new User(clientSocket);
                PlayerCommunication player = new PlayerCommunication(user,this,numberOfPlayersOfThisGame,"Player " + (numberOfPlayersOfThisGame + 1));

                while (!player.verify() && !clientSocket.isClosed()){
                    System.out.println("Login failed\nRetry.");
                }
                if(clientSocket.isClosed())
                    user.close();
                else {
                    waiting.add(player);
                    threadPool.execute(player);

                    totalNumberOfPlayers++;
                    //gameInstance = new GameInstance(gameThreads);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            game = new GameLogic(gameInstance);
                            game.startGame();

                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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
