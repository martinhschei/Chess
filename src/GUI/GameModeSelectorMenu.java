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
        main.setLayout(new GridLayout(3,0));
        JPanel infoBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        JLabel infoText = new JLabel("Choose your gamemode:");
        infoBox.add(infoText);
        JButton singleWindowSelector = SelectGameButton(singleWindow);
        JButton multiWindowSelector = SelectGameButton(multiWindow);
        main.add(infoBox);
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
