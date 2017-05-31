package login;

/**
 * Created by Karla on 28.05.2017.
 */
public interface DatabaseData {

    public final static String insertTable = "INSERT INTO Tables (TableID, NumberPlayers, Private, Name, Password) VALUES (?, ?, ?, ?, ?);";
    public final static String selectTables = "SELECT * FROM Tables WHERE game = false;";
    public final static String selectPlayersForTables = "SELECT * FROM PlayersFromTables WHERE TableID = ?;";
    public final static String insertIntoTable = "UPDATE Tables SET NumberPlayers = ? WHERE TableID = ?;";
    public final static String insertIntoPlayersFromTable = "INSERT INTO PlayersFromTables (TableID, NamePlayer) VALUES (?, ?);";
    public final static String deleteUser = "DELETE FROM PlayersFromTables WHERE NamePlayer = ?;";
    public final static String deleteTable = "DELETE FROM Tables WHERE TableID = ?;";


}