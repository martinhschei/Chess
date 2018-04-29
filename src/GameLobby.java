import java.util.Observable;
import java.util.Observer;

public class GameLobby implements Observer {

    HovedMeny menu = null;
    Player player = null;
    GameSettings settings = null;

    public GameLobby()
    {
        settings = new GameSettings();
        settings.addObserver(this);
        menu = new HovedMeny(settings);
    }

    public void update(Observable obj, Object arg)
    {

    }

}
