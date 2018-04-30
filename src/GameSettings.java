import java.util.Observable;

public class GameSettings extends Observable {

    private Player player = null;
    private String ip;

    public GameSettings()
    {
        player = new Player("player1", true);
    }

    public void setIp(String ip)
    {
        player.setIp(ip);
    }

    public void setReady (boolean ready)
    {
        player.setReady(ready);
        if (ready)
        {
            setChanged();
            notifyObservers(player);
        }
    }

    public void setPlayer(Player arg)
    {
        player = arg;
    }

    public void setHost()
    {
        player.setHost();
    }
}
