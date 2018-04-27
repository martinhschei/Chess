import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartMeny extends JFrame{
	public JPanel main = null;
	
	
	public StartMeny() 
	{
		main = new JPanel();
		FyllPanel(main);
		
	}
	
	private void FyllPanel(JPanel main2) {
		main.setLayout(new GridLayout(2,2));
		JPanel rute1 = new JPanel();
		JPanel rute2 = new JPanel();
		JPanel rute3 = new JPanel();
		JPanel rute4 = new JPanel();
		JButton host = LagHostButton();
		JButton join = LagJoinButton();
		JLabel ipaddr = LagIpAddr();
		rute1.setLayout(new GridLayout(2,0));
		rute1.add(host);
		rute1.add(join);
		rute4.add(ipaddr);
		main.add(rute1);
		main.add(rute2);
		main.add(rute3);
		main.add(rute4);
		
	}
	
	private JLabel LagIpAddr() {
		// TODO Auto-generated method stub
		return new JLabel("Din IpAdresse: Localhost");
	}
	
	private JButton LagJoinButton() {
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
	
	private JButton LagHostButton() {
		JButton retur = new JButton("Host Game");
		retur.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e)            
		    {
		        //Here goes the action (method) you want to execute when clicked
		        System.out.println("You clicked the button host");
		    }
		});
		return retur;
	}
	
	
}
