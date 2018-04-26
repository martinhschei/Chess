
public class Player {

	private final String name;
	private final boolean white;
	private final int id;
	
	public Player(String name, boolean white, int id)
	{
		this.name = name;
		this.white = white;
		this.id = id;
	}
	
	public boolean isWhite() 
	{
		return this.white;
	}
	
	
}
