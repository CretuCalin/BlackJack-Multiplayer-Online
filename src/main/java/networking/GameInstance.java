package networking;

import java.util.ArrayList;

/**
 * Created by calin on 25.03.2017.
 */

public class GameInstance {


    private PlayerCommunication[] threads;
    private int numberOfPlayers;
    private volatile int turn;


    private volatile boolean gameStarted;
    private volatile boolean gameOver;

    public GameInstance(PlayerCommunication[] threads, int n) {
        this.threads = threads;
        this.numberOfPlayers = n;
        this.turn = 0;
    }



    public PlayerCommunication[] getThreads() {
        return threads;
    }

    public void setThreads(PlayerCommunication[] threads) {
        this.threads = threads;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setGameStarted(boolean start){
        this.gameStarted = start;
    }

    public void setGameOver(boolean over){
        this.gameOver = over;
    }






}
