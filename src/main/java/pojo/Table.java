package pojo;

import javafx.print.PageLayout;
import networking.PlayerCommunication;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Karla on 09.05.2017.
 */
public class Table {

    private ArrayList<PlayerCommunication> players;
    private int numberPlayers;
    private PlayerCommunication admin;
    private boolean type;
    private String password;

    public int getID() {
        return ID;
    }

    private int ID;

    String name;

    private static ArrayList<Table> tables = new ArrayList<>();

    private int generateID(){
        int range = 1000;
        return (int)(Math.random() * range);
    }

    public Table(int n, String name, PlayerCommunication admin, boolean type, String password){
        players =  new ArrayList<>();
        this.numberPlayers = n;
        this.name = name;
        this.admin = admin;
        this.type = type;
        this.password = password;
        this.ID = generateID();
    }

    public boolean addPlayer(PlayerCommunication player){
        if(players.size() < numberPlayers){
            players.add(player);
            numberPlayers++;
            return true;
        }
        return false;
    }

    public static void addTable(Table table){
        tables.add(table);
    }

    public static Table existingTable(String table){
        for (int i = 0; i < tables.size(); i++)
            if (tables.get(i).getName().equals(table))
                return tables.get(i);
        return null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PlayerCommunication> getPlayers(){
        return players;
    }

    public int getNumberPlayers() {
        return players.size();
    }

}
