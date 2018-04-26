import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class NetworkServer extends Observable implements Runnable {
	
	private ServerSocket serverSocket;
	private int port;
	
	public NetworkServer(int port)
	{
		this.port = port;
	}
	
	public void run()
	{
		System.out.println("Server");
		Action action;
		try {
			this.serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			while(true) {
				try {
					action = (Action)ois.readObject();
					System.out.println(action.getType());
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

}
