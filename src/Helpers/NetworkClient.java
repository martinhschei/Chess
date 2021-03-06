package Helpers;

import Helpers.Action;
import Interfaces.IsActionListener;
import Interfaces.IsListener;
import Listeners.HasListeners;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkClient extends HasListeners implements Runnable, IsListener, IsActionListener {

	private final int port;
	private final String host;
	private ObjectOutputStream oos;

	public NetworkClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void onNewAction(Action action)
	{
		try {
			this.oos.writeObject(action);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onConnect()
	{
		this.onNewAction(new Action("hereiam", null));
	}

	@Override
	public void run() {
		System.out.println("Client up");
		try {
			Socket socket = new Socket(this.host, this.port);
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.onConnect();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(socket.isConnected()) {
				try {
					Action action = (Action) ois.readObject();
					this.publishAction(action);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
