import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NetworkServer implements Runnable {
	
	private ServerSocket serverSocket;
	private int port;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private List<Listener> listeners = new ArrayList<>();

	public NetworkServer(int port)
	{
		this.port = port;
	}

	public void addListener(Listener listener)
	{
		this.listeners.add(listener);
	}
	
	public void run()
	{
		System.out.println("Server up");
		Action action;
		try {
			this.serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
			this.ois = new ObjectInputStream(clientSocket.getInputStream());
			this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
			while(true) {
				try {
					action = (Action)ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notify(Action action) {
		for (Listener listener : listeners) {
			listener.onNewAction(action);
		}
	}
}
