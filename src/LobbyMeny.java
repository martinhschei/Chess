import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LobbyMeny extends JFrame{
	JPanel main = null;
	public LobbyMeny() 
	{
		main = new JPanel();
		FyllPanel(main);
	}
	private void FyllPanel(JPanel main2) {
		main.setLayout(new GridLayout(2,3));
		JPanel rute1 = new JPanel();
		JPanel rute2 = new JPanel();
		JPanel rute3 = new JPanel();
		JPanel rute4 = new JPanel();
		JPanel rute5 = new JPanel();
		JPanel rute6 = new JPanel();
		JButton ready = LagReadyButton();
		rute1.setLayout(new GridLayout(4,0));
		rute5.add(ready);
		main.add(rute1);
		main.add(rute2);
		main.add(rute3);
		main.add(rute4);
		main.add(rute5);
		main.add(rute6);
		
	}
	private JButton LagReadyButton() {
		JButton retur = new JButton("Join Game");
		retur.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e)            
		    {
		        //Here goes the action (method) you want to execute when clicked
		        System.out.println("You clicked the button join");
		    }
		});
		return retur;
	}
}
