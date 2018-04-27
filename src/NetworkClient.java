import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient extends HasActionListeners implements Runnable, IsActionListener {

	private int port;
	private String host;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public NetworkClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void newAction(Action action)
	{
		try {
			this.oos.writeObject(action);
		} catch (Exception e) {

		}
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
					Action action = (Action)this.ois.readObject();
					this.publishAction(action);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
