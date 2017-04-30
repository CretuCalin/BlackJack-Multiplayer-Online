package login;

import javax.xml.crypto.Data;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

/**
 * Created by bobby on 27-04-2017.
 */
public class Database {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://ihatethis.ddns.net:3306/MDS", "bobby", "goodBYE1!");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExits(String username, String password) throws NoSuchAlgorithmException {
        try {
            Statement statement = connection.createStatement();
            ResultSet results;// = statement.executeQuery(query);

            byte[] salt;
            String hash;
            String firstQuery = "Select Hash from Users where Username=\"" + username + "\"";
            results = statement.executeQuery(firstQuery);
            if (results.next()) {
                salt = results.getBytes(1);
            } else {
                return false;
            }

            String query = "Select Username from Users where Username=\"" + username + "\" AND Password=\"" + get_SHA_1_SecurePassword(password, salt) + "\"";
            results = statement.executeQuery(query);

            if (results.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean usernameExits(String username) {
        String query = "Select Username from Users where Username=\"" + username + "\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            if (results.next())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createNewUser(String username, String password) {
        String query = "Insert into Users (Username,Password,Points,Hash) values (?,?,?,?)";

        try {

            byte[] salt = getSalt();
            String generatedpasword = get_SHA_1_SecurePassword(password, salt);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, generatedpasword);
            preparedStatement.setInt(3, 0);
            preparedStatement.setBytes(4, salt);
            preparedStatement.execute();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private String get_SHA_1_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
