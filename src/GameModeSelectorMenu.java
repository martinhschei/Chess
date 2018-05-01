import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameModeSelectorMenu extends JFrame{
    private GameSettings gSettings;
    protected JPanel main;
    final private String multiWindow;
    final private String singleWindow;
    private MainMenu mainMenu;

    public GameModeSelectorMenu(GameSettings settings, MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
        multiWindow = "Dobbelt vindu (localhost game)";
        singleWindow = "Enkelt vindu (network game)";
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
        retur.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JButton source = (JButton)e.getSource();
                System.out.println("debug: " + source.getText());
                //Here goes the action (method) you want to execute when clicked
                if(source.getText().equalsIgnoreCase(singleWindow)) {
                    System.out.println("debug: SINGLE WINDOW");
                    mainMenu.SwitchToStartMenu();
                }
                if(source.getText().equalsIgnoreCase(multiWindow)) {
                    gSettings.startLocalGame();
                }
            }
        });
        return retur;
    }
}
