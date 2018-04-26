import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network implements Runnable  {

	private Socket clientSocket;
	private ServerSocket serverSocket;
	
	private ObjectOutputStream outputStream;
	
	private String host;
	private int port;
	
	
	public Network(int port)
	{
		this.port = port;   
	}
	
	public void connect() 
	{
		try {
			this.clientSocket = new Socket(this.host, this.port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(Action action)
	{
		if (this.clientSocket.isConnected()) {
			try {
				this.outputStream = (ObjectOutputStream)this.clientSocket.getOutputStream();
				this.outputStream.writeObject(action);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeServer()
	{
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
	}
	
}
