package managers;

import javafx.scene.control.Tab;
import pojo.Table;
import pojo.TablesForClient;

import java.util.ArrayList;

/**
 * Created by Karla on 09.05.2017.
 */
public class TablesManager implements Runnable{

    ArrayList<Table> tables;

    private static TablesManager instance;
    private TablesManager(){
        tables = new ArrayList<>();
    }

    public static TablesManager getInstance(){
        if(instance == null)
            instance = new TablesManager();
        return instance;
    }

    @Override
    public void run() {

    }

    public ArrayList<TablesForClient> printTables(){
        return DatabaseManager.getInstance().getTables();
    }

    public void addTable(Table table) {
        tables.add(table);
    }
}
