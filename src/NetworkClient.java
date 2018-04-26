import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class NetworkClient extends Observable implements Runnable, Observer {
	
	private String host;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public NetworkClient(String host, int port)
	{
		this.host = host;
		this.port = port;
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
					setChanged();
					notifyObservers(a);
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

	@Override
	public void update(Observable o, Object arg) {
		try {
			Action action = new Action("move", arg);
			this.oos.writeObject(action);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
