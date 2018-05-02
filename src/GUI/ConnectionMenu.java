package GUI;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import Config.*;
import Helpers.*;
import javax.swing.*;

class ConnectionMenu extends JFrame {

	private final GameSettings gameSettings;
	public final JPanel main;
	private JRadioButton hostButton;
	private JRadioButton joinButton;
	private JTextField nickNameField;
	private JTextField ipInputField;

	public ConnectionMenu(GameSettings settings)
	{
		gameSettings = settings;
		main = new JPanel();
		fillPanel();
	}

	private void fillPanel() {
		main.setLayout(new GridLayout(5,1));
		JPanel field1 = new JPanel();
		JPanel field2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
		JPanel field3 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
		JPanel field4 = new JPanel();
		JPanel field5 = new JPanel();
		JButton join = MonitorConnectButton();
		JLabel ipAddr = createIpLabel();

		//Textfield for IP input. Changes when joinButton isSelected();
		ipInputField = new JTextField(10);
		nickNameField = new JTextField(10);
		gameSettings.loadConfig();
		ipInputField.setText(gameSettings.getIp());
		nickNameField.setText(gameSettings.getNickName());
		ipInputField.setEditable(false);

		//bg1 = Buttongroup for Join og Host-knapp
		ButtonGroup bg1 = new ButtonGroup();
		joinButton = new JRadioButton("Join Game");
		joinButton.addActionListener(e -> ipInputField.setEditable(true));
		hostButton = new JRadioButton("Host Game");
		hostButton.addActionListener(e -> ipInputField.setEditable(false));

		bg1.add(hostButton);
		bg1.add(joinButton);
		field1.add(joinButton);
		field1.add(hostButton);

		field2.add(new JLabel("Set the IP-adress for connection"));
		field2.add(ipInputField);

		field3.add(new JLabel("Your nickname"));
		field3.add(nickNameField);
		field4.add(join);
		field5.add(ipAddr);

		main.add(field1);
		main.add(field2);
		main.add(field3);
		main.add(field4);
		main.add(field5);
	}

	private JLabel createIpLabel()
	{
		JLabel ipLabel = new JLabel();
		ipLabel.setText("Your local IP-adress is: " + this.getLocalIp());
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

	private JButton MonitorConnectButton()
	{
		JButton retur = new JButton("Connect and play");
		retur.addActionListener(e -> {
			//Here goes the action (method) you want to execute when clicked
			if(joinButton.isSelected()) {
				gameSettings.setPlayer(new Player(nickNameField.getText(), false));
				gameSettings.setIp(ipInputField.getText());
				gameSettings.startNetworkGame();
				gameSettings.saveSettings();
			}
			if(hostButton.isSelected() && !nickNameField.getText().equals("")) {
				gameSettings.setPlayer(new Player(nickNameField.getText(), true));
				gameSettings.setHost();
				gameSettings.startNetworkGame();
				gameSettings.saveSettings();
			}
		});
		return retur;
	}
}