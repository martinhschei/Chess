import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient implements Runnable {
	
	private String host;
	private int port;
	private Socket socket;
	private ObjectOutputStream outputStream;
	
	public NetworkClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void run() {
		System.out.println("Client");
		try {
			
			this.socket = new Socket(this.host, this.port);
			this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
			Action action = new Action("connected");
			this.outputStream.writeObject(action);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
