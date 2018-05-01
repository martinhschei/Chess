import javax.swing.JFrame;

class MainMenu extends JFrame {

	private JFrame mainWindow;
	private ConnectionMenu connectionMenu;
	private GameModeSelectorMenu gameModeMenu;

	public MainMenu(GameSettings settings)
	{
		mainWindow = buildWindow();
		connectionMenu = new ConnectionMenu(settings);
        gameModeMenu = new GameModeSelectorMenu(settings, this);
		mainWindow.setContentPane(gameModeMenu.main);
		mainWindow.setVisible(true);
	}

	private JFrame buildWindow()
	{
		JFrame frame = new JFrame("Startmeny for LotionChess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 500, 480, 300);
		frame.setLocationRelativeTo(null);
		return frame;
	}

	protected void removeMenu()
	{
		this.mainWindow.setVisible(false);
		this.mainWindow.setContentPane(null);
		this.mainWindow.dispose();
	}

	protected void switchToStartMenu()
	{
		this.mainWindow.setContentPane(connectionMenu.main);
		this.mainWindow.revalidate();
	}

}