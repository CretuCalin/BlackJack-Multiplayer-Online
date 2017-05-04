package networking;

import gamelogic.GameLogic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
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

    private int numberOfPlayers;
    private volatile boolean running;

    private final int portNumber = 9797;

    private volatile int turn;

    private volatile boolean gameStarted;
    private volatile boolean gameOver;

    private GameLogic game;

    private Timer timer;


    public Server(){

        threadPool = Executors.newCachedThreadPool();
        threads = new ArrayList<PlayerCommunication>();
        numberOfPlayers = 0;
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

        Date timeToRun;

        while (running && numberOfPlayers < 4)
        {
            try {
                clientSocket = serverSocket.accept();
                threads.add(new PlayerCommunication(clientSocket,this,numberOfPlayers,"Player " + (numberOfPlayers + 1)));
                threadPool.execute(threads.get(threads.size() - 1));
                System.out.println("player connected");
                numberOfPlayers++;

                if (numberOfPlayers == 1)
                {
                    int interval = 10000; // 10 sec
                    timeToRun = new Date(System.currentTimeMillis() + interval);
                    timer = new Timer();

                    timer.schedule(new TimerTask() {
                        public void run() {
                            // Task here ...
                            running = false;

                            try {
                                serverSocket.close();
                            } catch (IOException e) {
                                System.out.println("No more clients can connect!");
                            }
                            timer.cancel();
                        }
                    }, timeToRun);
                }
            } catch (IOException e) {
                System.out.println("No more clients can connect");
            }

        }


        game = new GameLogic(this);
        game.startGame();

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ArrayList<PlayerCommunication> getThreads() {
        return threads;
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

    public GameLogic getGame() {
        return game;
    }
}
