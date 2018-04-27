import java.io.Serializable;

public class Action implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Object payload;
	private String type;
	private Player player;
	
	public Action(String type, Object payload, Player player)
	{
		this.type = type;
		this.payload = payload;
		this.player = player;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public Object getPayload()
	{
		return this.payload;
	}

	public Player getPlayer()
	{
		return this.player;
	}
	

}
