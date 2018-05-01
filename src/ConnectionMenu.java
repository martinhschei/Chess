import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

class ConnectionMenu extends JFrame {

    private GameSettings gameSettings;
	public JPanel main;
	private JRadioButton hostButton;
	private JRadioButton joinButton;
	private JTextField nickNameField;
	private JTextField ipInputField;

	public ConnectionMenu(GameSettings settings)
	{
		gameSettings = settings;
		main = new JPanel();
		fillPanel(main);
	}

	private void fillPanel(JPanel main2) {
		main.setLayout(new GridLayout(2,2));
		JPanel field1 = new JPanel();
		field1.setLayout(new GridLayout(4,0));
		JPanel field2 = new JPanel();
		JPanel field3 = new JPanel();
		JPanel field4 = new JPanel();
		JButton join = monitorConnectButton();
		JLabel ipAddr = createIpLabel();

		//Textfield for IP input. Changes when joinButton isSelected();
		ipInputField = new JTextField(10);
		nickNameField = new JTextField(10);
		gameSettings.loadConfig();
		ipInputField.setText(gameSettings.getIp());
		nickNameField.setText(gameSettings.getNickName());
		ipInputField.setEditable(false);

		//NameInputField.setEditable(true);
		ButtonGroup bg1 = new ButtonGroup();
		hostButton = new JRadioButton("Host Game");
		joinButton = new JRadioButton("Join Game");
		joinButton.addActionListener(e -> ipInputField.setEditable(true));
		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipInputField.setEditable(false);
			}
		});

		bg1.add(hostButton);
		bg1.add(joinButton);
		field1.add(joinButton);
		field1.add(hostButton);

		field2.add(new JLabel("Skriv IP-adresse du koble deg til"));
		field2.add(ipInputField);

		field3.add(new JLabel("Skriv ditt nickname"));
		field3.add(nickNameField);
		field3.add(join);
		field3.add(ipAddr);

		main.add(field1);
		main.add(field2);
		main.add(field3);
		main.add(field4);
	}

	private JLabel createIpLabel()
	{
		JLabel ipLabel = new JLabel();
		ipLabel.setText("Din lokale ip adresse er: " + this.getLocalIp());
		return ipLabel;
	}

	private String getLocalIp()
	{
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	private JButton monitorConnectButton()
	{
		JButton btnConnect = new JButton("Connect to Game");
		btnConnect.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        //Here goes the action (method) you want to execute when clicked
		        System.out.println("You clicked the connecto buttono");
		        if(joinButton.isSelected()) {
					System.out.println("debug: joinbutton isselected. FUNGERER");
					gameSettings.setPlayer(new Player(nickNameField.getText(), false));
					gameSettings.setIp(ipInputField.getText());
					gameSettings.setReady(true);
					gameSettings.saveSettings();
				}
				if(hostButton.isSelected() && !nickNameField.getText().equals("")) {
					System.out.println("debug: hostbutton isselected. FUNGERER");
					gameSettings.setPlayer(new Player(nickNameField.getText(), true));
					gameSettings.setHost();
					gameSettings.setReady(true);
					gameSettings.saveSettings();
				}
		    }
		});
		return btnConnect;
	}
}
