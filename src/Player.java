import java.io.Serializable;

public class Player implements Serializable {

	private final String name;
	private final boolean white;
    private boolean host;
	
	public Player(String name, boolean white, int id)
	{
		this.name = name;
		this.white = white;
        int id1 = id;
		this.host = false;
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
}
