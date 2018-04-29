import java.util.Observable;
import java.util.Observer;

public class GameLobby implements Observer{

    HovedMeny menu = null;
    Player player = null;

    public GameLobby(){
        menu = new HovedMeny();

    }
    public void update(Observable obj, Object arg)
    {
    }

}
