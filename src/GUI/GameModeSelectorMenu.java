package GUI;

import javax.swing.*;
import Config.*;
import java.awt.*;

class GameModeSelectorMenu extends JFrame{
    private final GameSettings gSettings;
    final JPanel main;
    final private String multiWindow;
    final private String singleWindow;
    private final MainMenu mainMenu;

    public GameModeSelectorMenu(GameSettings settings, MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
        multiWindow = "Double-windowed localhost game";
        singleWindow = "Single-windowed network game";
        gSettings = settings;
        main = new JPanel();
        FyllPanel(main);
    }

    private void FyllPanel(JPanel main) {
        main.setLayout(new GridLayout(2,0));
        JButton singleWindowSelector = SelectGameButton(singleWindow);
        JButton multiWindowSelector = SelectGameButton(multiWindow);
        main.add(singleWindowSelector);
        main.add(multiWindowSelector);
    }

    private JButton SelectGameButton(String name) {
        JButton retur = new JButton(name);
        retur.addActionListener(e -> {
            JButton source = (JButton)e.getSource();
            if(source.getText().equalsIgnoreCase(singleWindow)) {
                mainMenu.SwitchToStartMenu();
            }
            if(source.getText().equalsIgnoreCase(multiWindow)) {
                gSettings.startLocalGame();
            }
        });
        return retur;
    }
}
