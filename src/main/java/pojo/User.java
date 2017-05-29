package pojo;

import javafx.scene.control.Tab;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Karla on 07.05.2017.
 */
public class User {

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String username;
    private Socket socket;
    private String password;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    private Table table;

    private boolean admin;

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }


    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public User(Socket socket) throws IOException {
        this.socket = socket;
    }

    public User(){

    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public boolean isAdmin(){
        return admin;
    }

    public void close(){
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
