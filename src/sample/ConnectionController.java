package sample;

import java.io.*;
import java.net.Socket;

/**
 * Created by Rares on 5/21/2017.
 */
public class ConnectionController {

    Socket socket;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    private static ConnectionController instance;

    public static ConnectionController getInstance(){
        if(instance == null)
            instance = new ConnectionController();
        return instance;
    }

    public ConnectionController() {
        /*
        try {
            socket = new Socket("192.168.1.137", 9998);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            objectOutputStream.writeObject("Aici Rares");
            System.out.print(objectInputStream.readObject());

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        */
    }

    public String requestHit(){
        String s = "ERROR";
        try{
            objectOutputStream.writeObject("REQUEST HIT");
            s = (String) objectInputStream.readObject();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return s;
    }

    public String requestStand(){
        String s = "ERROR";
        try{
            objectOutputStream.writeObject("REQUEST STAND");
            s = (String) objectInputStream.readObject();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return s;
    }

    public boolean sendLoginData(String text, String text1) {
        //TODO Implement
        System.out.println(text+ " " + text1);
        return true;
    }

    public void createTable(String tableName, String numberOfPlayers, String timeOfMoves, boolean privacy, String password){

    }
}
