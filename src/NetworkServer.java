import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class NetworkServer extends Observable implements Runnable, Observer {
	
	private ServerSocket serverSocket;
	private int port;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public NetworkServer(int port)
	{
		this.port = port;
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
					setChanged();
					notifyObservers(action);
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
