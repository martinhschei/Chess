package GUI;

import Config.*;
import Helpers.*;
import java.util.Observable;
import java.util.Observer;

public class GameLobby implements Observer {

    private final MainMenu mainMenu;
    private final GameSettings settings;

    public GameLobby()
    {
        settings = new GameSettings();
        settings.addObserver(this);
        mainMenu = new MainMenu(settings);
    }

    public void update(Observable obj, Object arg)
    {
        settings.saveSettings();
        new Game((Player)arg);
        mainMenu.RemoveMenu();
    }

}
