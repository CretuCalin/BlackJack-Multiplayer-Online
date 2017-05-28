package networking;

/**
 * Created by Karla on 09.05.2017.
 */
public class MessagesInterpreter {

    private static MessagesInterpreter instance;

    public static MessagesInterpreter getInstance(){
        if(instance == null)
            instance = new MessagesInterpreter();
        return instance;
    }

    public boolean wantsToJoinTable(String message){
        if(message.equals("JOIN TABLE"))
            return true;
        return false;
    }

    public boolean wantsToCreateTable(String message){
        if(message.equals("CREATE TABLE"))
            return true;
        return false;
    }

}
