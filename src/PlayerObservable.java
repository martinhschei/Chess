import java.util.Observable;

public class PlayerObservable extends Observable{
    private Player player=null;

    public PlayerObservable(){

    }

    public Player getPlayer()
    {return player;}

    public void setPlayer (Player input)
    {
        this.player = input;
        if (this.player != null)
        {
            setChanged();
            notifyObservers(player);
        }
    }
}
