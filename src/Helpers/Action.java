package Helpers;

import java.io.Serializable;

public class Action implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Object payload;
	private final String type;
	
	public Action(String type, Object payload)
	{
		this.type = type;
		this.payload = payload;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public Object getPayload()
	{
		return this.payload;
	}

}
