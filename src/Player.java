import java.io.Serializable;

public class Player implements Serializable {

	private final String name;
	private final boolean white;
    private boolean host;
    private boolean ready;
	
	public Player(String name, boolean white)
	{
		this.name = name;
		this.white = white;
		this.host = false;
		ready = false;
	}
	
	public boolean isWhite() 
	{
		return this.white;
	}
	
	public boolean isHost()
	{
		return this.host;
	}
	
	public void setHost()
	{
		this.host = true;
	}

	public String getName()
	{
		return this.name;
	}

	public void setReady(boolean ready) { this.ready = ready;}

	public boolean isReady() {return this.ready;}
}
