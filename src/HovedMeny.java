import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
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
	//PlayerObservable player = null;
	
	public HovedMeny(GameSettings settings)
	{
        JFrame hovedVindu = BuildWindow();
        JPanel startMeny = new StartMeny(settings).main;
        //JPanel lobbyMeny = new LobbyMeny().main;
		hovedVindu.setContentPane(startMeny);

		//startMeny= new StartMeny();
		//lobbyMeny = new LobbyMeny().main;
		hovedVindu.setVisible(true);
	}

	/*private StartMeny buildStartMeny() {
		StartMeny retur = new StartMeny(player);
		retur.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("debug: propertychangeevent"+evt.getPropertyName());
				if (evt.getPropertyName()=="playerReady")
				{
					System.out.println("debug: Player READY!!!");
				}
			}
		});
		return retur;
	}*/


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