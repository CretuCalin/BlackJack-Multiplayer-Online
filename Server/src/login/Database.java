package login;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Created by bobby on 27-04-2017.
 */
public class Database {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Database(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://ihatethis.ddns.net:3306/testbd","bobby","goodBYE1!");



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
