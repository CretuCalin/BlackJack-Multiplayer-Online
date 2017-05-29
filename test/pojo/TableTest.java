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

    @Test
    void addTable() {
        int random = new Random().nextInt(50);
        PlayerCommunication playerCommunication = new PlayerCommunication(new User(),0,"testName");
        for (int i=0;i<random;i++){
            Table table = new Table(new Random().nextInt(100),String.valueOf(new Random().nextInt(100)),playerCommunication,false,null);
        }

    }

    @Test
    void createPersonalTable() {
        PlayerCommunication playerCommunication = new PlayerCommunication(new User(),0,"testName");
        for (int i=0;i< new Random().nextInt(50);i++){
            playerCommunication.createTableTest(String.valueOf(new Random().nextInt(100)),new PlayerCommunication(new User(),new Random().nextInt(100),String.valueOf(new Random().nextInt(100))));
        }
    }

}

