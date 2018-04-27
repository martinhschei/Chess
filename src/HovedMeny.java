import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HovedMeny extends JFrame {

    private JPanel tilkoblingsMeny = null;
	
	public HovedMeny() 
	{
        JFrame hovedVindu = BuildWindow();
        JPanel startMeny = new StartMeny().main;
        JPanel lobbyMeny = new LobbyMeny().main;
		hovedVindu.setContentPane(startMeny);
		hovedVindu.setVisible(true);
	}

	private JFrame BuildWindow()
	{
		JFrame frame = new JFrame("Startmeny");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 500, 500, 500);
		//frame.pack();
		return frame;
	}
	
	
	
}