package Helpers;

import Helpers.*;
import Interfaces.*;
import Listeners.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer extends HasListeners implements Runnable, IsActionListener, IsListener {

    private final int port;
    private ObjectOutputStream oos;

	public NetworkServer(int port)
	{
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

	public void run()
	{
		System.out.println("Server up");
		try {
            ServerSocket serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
			System.out.println("New player connected");
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
			while(clientSocket.isConnected()) {
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
