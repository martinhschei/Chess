import java.util.Observable;

public class GameSettings extends Observable {

    private Player player = null;

    public GameSettings(){}

    public void setReady (boolean ready)
    {
        player.setReady(ready);
        if (ready)
        {
            setChanged();
            notifyObservers(player);
        }
    }
}
