package pojo;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Karla on 28.05.2017.
 */
public class TablesForClient implements Serializable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPlayersNames() {
        return playersNames;
    }

    public void addPlayersNames(String playerNames) {
        this.playersNames.add(playerNames);
    }

    private ArrayList<String> playersNames;

    public TablesForClient(String name){
        this.name = name;
        playersNames = new ArrayList<>();
    }

}
