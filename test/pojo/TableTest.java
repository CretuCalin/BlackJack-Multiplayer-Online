package pojo;

import networking.PlayerCommunication;
import networking.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bobby on 29-05-2017.
 */




class TableTest {
//    @Test
//    void addPlayer(Table table) throws IOException {
//        for (int i = 0; i<new Random().nextInt(10); i++){
//            PlayerCommunication playerCommunication = new PlayerCommunication(new User(null),null,0,"noname");
//            table.addPlayer(playerCommunication);
//        }
//
//    }
//
//    @Test
//    void addTable() throws IOException {
//        PlayerCommunication playerCommunication = new PlayerCommunication(new User(null),new Server(),0,"noname");
//
//        for (int i = 0; i<new Random().nextInt(10); i++){
//            Table table = new Table(new Random().nextInt(6),"test",playerCommunication,false,"");
//            Table.addTable(table);
//            addPlayer(table);
//        }
//
//    }
    @Test

        void define() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1");
                Server server = new Server();
                server.waitForConnections();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t2");
                    Socket socket = new Socket("localhost", 9998);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

       t1.start();
       t2.start();
    }

}

