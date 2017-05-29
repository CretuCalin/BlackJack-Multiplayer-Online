package login;

import managers.DatabaseManager;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bobby on 29-05-2017.
 */
class LoginTest {
    @org.junit.jupiter.api.Test
    void verify() throws NoSuchAlgorithmException, SQLException {
        for (int i = 0; i < 10; i++)
                generators();
        DatabaseManager.getInstance().deletePlayersTest(100);

    }

    void generators(){
        SessionIdentifierGenerator id = new SessionIdentifierGenerator();

        for (int i = 0; i < 10; i++) {
            String name = id.nextSessionId();
            String pass = id.nextSessionId();
           DatabaseManager.getInstance().createNewUser(name, pass);
            try {
                assertEquals("CORRECT AUTHENTICATION", DatabaseManager.getInstance().userExits(name, pass));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
    }

    final  class  SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();

        public synchronized String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }
}