package networking;

/**
 * Created by calin on 21.03.2017.
 */
public class Main {
    public static void main(String argc[])
    {
        Server server = new Server();
        server.waitForConnections();
    }
}
