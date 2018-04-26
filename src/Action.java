import java.io.Serializable;

public class Action implements Serializable {
	
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
	
	public Action(Object object, String type, Player player)
	{
		this.payload = object;
		this.type = type;
		this.player = player;
	}
	
	public Object getPayloadObject()
	{
		switch(this.type) {
			
			case("move") : {
				return (Move)this.payload;
			}
			
			default: {
				// make a fault object
				return null;
			}
	
		}
		
	}
}
