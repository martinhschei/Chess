import java.io.Serializable;
import java.util.Observable;

public class Action extends Observable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Object payload;
	private String type;
	private Player player;
	
	public Action(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public Object getPayload()
	{
		return this.payload;
	}
	
	public Action(Object object, String type, Player player)
	{
		this.payload = object;
		this.type = type;
		this.player = player;
	}
	

}
