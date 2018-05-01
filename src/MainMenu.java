import javax.swing.JFrame;
import javax.swing.JPanel;

class MainMenu extends JFrame {

	private JFrame hovedVindu = null;
	StartMeny startMeny;
	GameModeSelectorMenu gameModeMenu;
	
	public MainMenu(GameSettings settings)
	{
        hovedVindu = BuildWindow();
        startMeny = new StartMeny(settings);
        gameModeMenu = new GameModeSelectorMenu(settings, this);
		hovedVindu.setContentPane(gameModeMenu.main);
		hovedVindu.setVisible(true);
	}

	private JFrame BuildWindow()
	{
		JFrame frame = new JFrame("Startmeny for LotionChess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 500, 400, 300);
		frame.setLocationRelativeTo(null);
		//frame.pack();
		return frame;
	}

	protected void RemoveMenu()
	{
		this.hovedVindu.setVisible(false);
		this.hovedVindu.setContentPane(null);
		this.hovedVindu.dispose();
	}
	protected void SwitchToStartMenu()
	{
		this.hovedVindu.setContentPane(startMeny.main);
		this.hovedVindu.revalidate();
	}
	
	
}