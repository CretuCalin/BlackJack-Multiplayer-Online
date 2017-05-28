package networking;

import java.util.ArrayList;

/**
 * Created by calin on 25.03.2017.
 */

public class GameInstance {


    private ArrayList<PlayerCommunication> threads;
    private int numberOfPlayers;
    private volatile int turn;


    public boolean isGameStarted() {
        return gameStarted;
    }

    private volatile boolean gameStarted;
    private volatile boolean gameOver;

    public GameInstance(ArrayList<PlayerCommunication> threads, int n) {
        this.threads = threads;
        this.numberOfPlayers = n;
        this.turn = 0;
    }



    public ArrayList<PlayerCommunication> getThreads() {
        return threads;
    }

    public int getNumberOfPlayers() {
        return threads.size();
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
