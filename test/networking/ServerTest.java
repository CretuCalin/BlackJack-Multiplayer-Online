package networking;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bobby on 29-05-2017.
 */
class ServerTest {
    Server server=null;

    @Test
    void waitForConnections() throws IOException {

       // startServer();
        for (int i = 0; i < 15; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Socket socket = new Socket("10.11.48.101", 9999);
                        assertEquals(true,socket.isConnected());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
//        for (int i = 0; i < 15; i++) {
//            t.start();
//        }
    }

//    void startServer(){
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
//                 server = new Server();
//                server.waitForConnections();
////            }
////        }).start();
//    }

}