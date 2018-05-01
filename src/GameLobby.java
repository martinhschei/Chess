import java.util.Observable;
import java.util.Observer;

class GameLobby implements Observer {

    MainMenu menu;
    GameSettings settings;

    public GameLobby()
    {
        settings = new GameSettings();
        settings.addObserver(this);
        menu = new MainMenu(settings);
    }

    public void update(Observable obj, Object arg)
    {
        new Game((Player)arg);
        menu.RemoveMenu();
    }

}
