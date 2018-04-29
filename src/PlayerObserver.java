import java.util.Observable;
import java.util.Observer;

public class PlayerObserver implements Observer {
    private Player player = null;
    public PlayerObserver(){}

    public void update(Observable obj, Object arg)
    {
        if (arg instanceof Player)
        {
            player = (Player) arg;
            System.out.println("debug: PlayerObserver! " + player.toString());
        }
    }

}
