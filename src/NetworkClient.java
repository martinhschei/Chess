import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class NetworkClient implements Runnable {

	private int port;
	private String host;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private List<Listener> listeners = new ArrayList<>();

	public NetworkClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void addListener(Listener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void run() {
		System.out.println("Client up");
		try {
			this.socket = new Socket(this.host, this.port);
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
			while(true) {
				try {
					Action a = (Action)this.ois.readObject();
					notify(a);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void notify(Action action) {
		for (Listener listener : listeners) {
			listener.onNewAction(action);
		}
	}

}
