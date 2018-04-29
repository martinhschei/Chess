import java.util.Observable;

public class GameSettings extends Observable {

    private Player player = null;

    public GameSettings(){
        player = new Player("player1", true);
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

    public void setHost(){
        player.setHost();
    }
}
