import java.util.Observable;
import java.util.Observer;

class GameLobby implements Observer {

    MainMenu menu = null;
    GameSettings settings = null;

    public GameLobby()
    {
        settings = new GameSettings();
        settings.addObserver(this);
        menu = new MainMenu(settings);
    }

    public void update(Observable obj, Object arg)
    {
        settings.saveSettings();
        new Game((Player)arg);
        menu.RemoveMenu();
    }

}
