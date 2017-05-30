package sample;

import pojo.Card;
import pojo.TablesForClient;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Rares on 5/21/2017.
 */
public class ConnectionController {

    Socket socket;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    private static ConnectionController instance;

    public static ConnectionController getInstance(){
        if(instance == null){
            instance = new ConnectionController();
        }
        return instance;
    }

    public ConnectionController() {

        try {
            socket = new Socket("192.168.1.137", 9998);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Card requestHit(){
        Card s = null;
        try{
            System.out.println("Pus");
            objectOutputStream.writeObject("HIT");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return s;
    }

    public String requestStand(){
        String s = "ERROR";
        try{
            objectOutputStream.writeObject("STAND");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return s;
    }

    public String sendLoginData(String username, String password) {
        String response = "";
        try{
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);
            response = (String) objectInputStream.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return response;
    }

    public void sendStartGame(){
        try{
            objectOutputStream.writeObject("START GAME");
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public Object read(){
        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSomeText(){
        String response = "";
        try{
            response = (String) objectInputStream.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return response;
    }

    public int getSomeInt(){
        int response = -1;
        try{
            response = (int) objectInputStream.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return response;
    }

    public Card getSomeCard(){
        Card card = null;
        try{
            card = (Card) objectInputStream.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return card;
    }



    public int createTable(String tableName,
                           String numberOfPlayers,
                           String timeOfMoves,
                           boolean privacy,
                           String password){
        int idTable = 0;
        try{
            objectOutputStream.writeObject("CREATE TABLE");
            objectOutputStream.writeObject(tableName);
            objectOutputStream.writeObject(numberOfPlayers);
            //objectOutputStream.writeObject(timeOfMoves);
            objectOutputStream.writeObject(privacy);
            objectOutputStream.writeObject(password);
            idTable = (int) objectInputStream.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return idTable;
    }

    public ArrayList<TablesForClient> getTablesList(){
        ArrayList<TablesForClient> arr = null;
        try{
            arr = (ArrayList<TablesForClient>) objectInputStream.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return arr;
    }

    public void joinTable(String name,String password) throws IOException {
        objectOutputStream.writeObject("JOIN TABLE");
        objectOutputStream.writeObject(name);
    }


}
