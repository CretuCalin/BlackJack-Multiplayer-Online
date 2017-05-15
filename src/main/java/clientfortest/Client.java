package clientfortest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 Created by calin on 04.05.2017.
 */
public class Client {


    public static void main(String argc[]){

        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;

        try {
            socket = new Socket("localhost",9998);
            boolean running = true;
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);
            String reply;

            String message = (String) in.readObject();
            System.out.println(message);
            out.writeObject("username2");
            out.flush();
            message = (String) in.readObject();
            System.out.println(message);
            out.writeObject("pass");
            out.flush();
            message = (String) in.readObject();
            System.out.println(message);
            message = (String) in.readObject();
            System.out.println(message);
            out.writeObject("Table 4");
            out.flush();



            while (running){
                message = (String) in.readObject();
                System.out.println(message);
                reply = sc.next();
                if (reply.equals("Exit"))
                    running = false;
                else {
                    out.writeObject(reply);
                    out.flush();
                }
            }

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
