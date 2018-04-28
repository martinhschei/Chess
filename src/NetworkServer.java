import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer extends HasActionListeners implements Runnable, IsActionListener {

    private int port;
    private ObjectOutputStream oos;

	public NetworkServer(int port)
	{
		this.port = port;
	}

    public void newAction(Action action)
    {
        try {
            this.oos.writeObject(action);
        } catch (Exception e) {

        }
    }

	public void run()
	{
		System.out.println("Server up");
		try {
            ServerSocket serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
			while(true) {
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
