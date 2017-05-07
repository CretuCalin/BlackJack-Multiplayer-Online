package networking;

import gamelogic.GameLogic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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

    private ArrayList<PlayerCommunication> threads;

    private int numberOfPlayersOfThisGame;
    private int totalNumberOfPlayers;
    private volatile boolean running;

    private final int portNumber = 12345;

    private volatile int turn;

    private volatile boolean gameStarted;
    private volatile boolean gameOver;

    private GameLogic game;
    private GameInstance gameInstance ;

    private final int numberOfPlayersPerGame = 4;


    public Server(){

        threadPool = Executors.newCachedThreadPool();
        threads = new ArrayList<PlayerCommunication>();
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


        while (running && totalNumberOfPlayers < 16)
        {
            System.out.println("waiting for connections");
            try {

                clientSocket = serverSocket.accept();
                threads.add(new PlayerCommunication(clientSocket,this,numberOfPlayersOfThisGame,"Player " + (numberOfPlayersOfThisGame + 1)));
                threadPool.execute(threads.get(threads.size() - 1));
                System.out.println("Player connected");

                totalNumberOfPlayers++;
                numberOfPlayersOfThisGame++;

                if(totalNumberOfPlayers % numberOfPlayersPerGame == 0){

                    ArrayList<PlayerCommunication> gameThreads = new ArrayList<>();
                    gameThreads.add(threads.get(threads.size()-1));
                    gameThreads.add(threads.get(threads.size()-2));
                    gameThreads.add(threads.get(threads.size()-3));
                    gameThreads.add(threads.get(threads.size()-4));

                    gameInstance = new GameInstance(gameThreads);

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
