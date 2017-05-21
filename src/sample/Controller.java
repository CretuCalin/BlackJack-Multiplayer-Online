package sample;

/**
 * Created by Rares on 5/21/2017.
 */
public class Controller {
    public static Controller instance;

    public static Controller getInstance(){
        if(instance == null){
            return new Controller();
        }
        else
            return instance;
    }

    public void hitRequest(CardManager cardManager){
        String response = ConnectionController.getInstance().requestHit();
        cardManager.addCard("/resources/deck/" + response + ".png");
    }

    public void standRequest(CardManager cardManager){
        String response = ConnectionController.getInstance().requestStand();
        cardManager.setResult(response);
    }
}
