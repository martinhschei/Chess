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
        if(settings.getIsLocalGame())
        {
            Player p1 = new Player(settings.getLocalPlayerWhite(), true);
            p1.setHost();
            p1.setReady(true);
            Player p2 = new Player(settings.getLocalPlayerBlack(), false);
            p2.setIp("localhost");
            p2.setReady(true);
            new Game(p1);
            new Game(p2);
        }
        else{
            new Game(settings.getPlayer());
        }

        mainMenu.RemoveMenu();
    }

}
