package clientfortest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by calin on 04.05.2017.
 */
public class Client {


    public static void main(String argc[]){

        Socket socket = null;
        ObjectInputStream in;
        ObjectOutputStream out;
        try {
            socket = new Socket("localhost",12345);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
