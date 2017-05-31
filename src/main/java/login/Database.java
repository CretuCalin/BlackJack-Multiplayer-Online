package login;

import pojo.Table;
import pojo.TablesForClient;
import pojo.User;

import javax.xml.crypto.Data;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;

/**
 Created by bobby on 27-04-2017.
 */
public class Database {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Database() {
       connect();
    }

    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://ihatethis.ddns.net:3306/MDS?autoReconnect=true&useSSL=false", "karla", "proiectMDS1!");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String name){
        try {
            PreparedStatement statement = connection.prepareStatement(DatabaseData.deleteUser);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable(int ID){
        try {
            PreparedStatement statement = connection.prepareStatement(DatabaseData.deleteTable);
            statement.setInt(1, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
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

    public String userExits(String username, String password) throws NoSuchAlgorithmException {
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
                return "User Dosen't exist";
            }


            String query = "Select Username from Users where Username=\"" + username + "\" AND Password=\"" + get_SHA_1_SecurePassword(password, salt) + "\"";
            results = statement.executeQuery(query);

            if (results.next())
                return "CORRECT AUTHENTICATION";
            else  return "Wrong Password";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "An error has occurd";
    }

    public void addToTable(Table table, User user){
        if(table == null){
            System.out.println("Table is null");
        }
        if(user == null){
            System.out.println("user is null");
        }
        try{

            PreparedStatement statement = connection.prepareStatement(DatabaseData.insertIntoTable);
            PreparedStatement statement1 = connection.prepareStatement(DatabaseData.insertIntoPlayersFromTable);
            if(statement != null || statement1 != null){

                statement.setInt(1, table.getNumberPlayers());
                statement.setInt(2,table.getID());
                statement.executeUpdate();

                statement1.setInt(1, table.getID());
                statement1.setString(2, user.getUsername());
                statement1.executeUpdate();
            }else{
                System.out.println("Statement is null");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    public boolean createNewTable(Table table) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DatabaseData.insertTable)){
            preparedStatement.setInt(1, table.getID());
            preparedStatement.setInt(2, table.getNumberPlayers());
            preparedStatement.setBoolean(3, table.isPrivate());
            preparedStatement.setString(4, table.getName());
            preparedStatement.setString(5, table.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<TablesForClient> getTables(){
        //TODO get name and players from DATABASE
        ArrayList<TablesForClient> tables = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(DatabaseData.selectTables);ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                TablesForClient table = new TablesForClient(rs.getString("Name"));
                PreparedStatement statement1 = connection.prepareStatement(DatabaseData.selectPlayersForTables);
                statement1.setInt(1, rs.getInt("TableID"));
                ResultSet rs2 = statement1.executeQuery();
                while (rs2.next()){
                    table.addPlayersNames(rs2.getString("NamePlayer"));
                }
                tables.add(table);
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
