import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HovedMeny extends JFrame {

	private JFrame hovedVindu = null;
	private StartMeny startMeny = null;
	//private JPanel lobbyMeny = null;
	//private JPanel tilkoblingsMeny = null;
	
	public HovedMeny(GameSettings settings)
	{
        JFrame hovedVindu = BuildWindow();
        JPanel startMeny = new StartMeny(settings).main;

		hovedVindu.setContentPane(startMeny);

		//startMeny= new StartMeny();
		//lobbyMeny = new LobbyMeny().main;
		hovedVindu.setVisible(true);
	}




	private JFrame BuildWindow()
	{
		JFrame frame = new JFrame("Startmeny");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 500, 500, 500);
		frame.setLocationRelativeTo(null);
		//frame.pack();
		return frame;
	}

	
	
}