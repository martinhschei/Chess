package GUI;

import Config.*;
import GUI.*;

import javax.swing.JFrame;

class MainMenu extends JFrame {

	private final JFrame hovedVindu;
	private final ConnectionMenu connectionMenu;

	public MainMenu(GameSettings settings)
	{
        hovedVindu = BuildWindow();
		connectionMenu = new ConnectionMenu(settings);
        GameModeSelectorMenu gameModeMenu = new GameModeSelectorMenu(settings, this);
		hovedVindu.setContentPane(gameModeMenu.main);
		hovedVindu.setVisible(true);
	}

	private JFrame BuildWindow()
	{
		JFrame frame = new JFrame("LotionChess™");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(380, 250);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		return frame;
	}

	void RemoveMenu()
	{
		this.hovedVindu.setVisible(false);
		this.hovedVindu.dispose();
	}
	void SwitchToStartMenu()
	{
		this.hovedVindu.setContentPane(connectionMenu.main);
		this.hovedVindu.revalidate();
	}
	
	
}