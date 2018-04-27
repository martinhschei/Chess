import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StartMeny extends JFrame{
	public JPanel main = null;
	private JRadioButton hostButton;
	private JRadioButton joinButton;
	private JTextField NameInputField = new JTextField(10);
	private boolean White = true;
	PlayerObservable player = null;
	
	public StartMeny(PlayerObservable player1)
	{
		player = player1;
		main = new JPanel();
		FyllPanel(main);
		
	}
	
	private void FyllPanel(JPanel main2) {
		main.setLayout(new GridLayout(2,2));
		JPanel rute1 = new JPanel();
		JPanel rute2 = new JPanel();
		JPanel rute3 = new JPanel();
		JPanel rute4 = new JPanel();
		JButton join = MonitorConnectButton();
		JLabel ipaddr = LagIpAddr();
		rute1.setLayout(new GridLayout(2,0));
		//Textfield for IP input. Changes when joinButton isSelected();
		JTextField ipInputField = new JTextField(10);
		ipInputField.setEditable(false);
		NameInputField.setEditable(true);
		ButtonGroup bg1 = new ButtonGroup();
		hostButton = new JRadioButton("Host Game");
		joinButton = new JRadioButton("Join Game");
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ipInputField.setEditable(true);
			}
		});
		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipInputField.setEditable(false);
			}
		});

		bg1.add(hostButton);
		bg1.add(joinButton);
		rute1.add(joinButton);
		rute1.add(hostButton);
		rute2.add(new JLabel("Skriv IP-adresse du vil connecte mot"));
		rute2.add(ipInputField);
		rute3.add(join);
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
	
	private JButton MonitorConnectButton() {
		JButton retur = new JButton("Connect to Game");
		retur.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e)            
		    {
		    	NameInputField.setText("playaaaa");
		        //Here goes the action (method) you want to execute when clicked
		        System.out.println("You clicked the connecto buttono");
		        if(joinButton.isSelected()) {
					System.out.println("debug: joinbutton isselected. FUNGERER");
					player.setPlayer(new Player(NameInputField.getText(), false, 2));

					// TODO: Åpne vindu for join game
				}
				if(hostButton.isSelected()) {
					System.out.println("debug: hostbutton isselected. FUNGERER");
					// TODO: Åpne vindu for join game
					player.setPlayer(new Player(NameInputField.getText(), true, 1));

				}
		    }
		});
		return retur;
	}
}
