import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Network {

	private Socket connectSocket;
	private ServerSocket serverSocket;
	private String host;
	private int port;
	
	public Network(String host, int port)
	{
		this.host = host;
		this.port = port;
		   
	}
	
	
	public Network(int port)
	{
		this.port = port;   
	}
	
	public void connect() 
	{
		try {
			this.connectSocket = new Socket(this.host, this.port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen()
	{
		try {
			this.serverSocket = new ServerSocket(1337);
			Socket clientSocket = serverSocket.accept();
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			while(true) {
				try {
					String input = (String)ois.readObject();
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
	
	public void close()
	{
		try {
			this.connectSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
