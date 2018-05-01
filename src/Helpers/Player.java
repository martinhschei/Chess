package Helpers;

import java.io.Serializable;

public class Player implements Serializable {

	private final String name;
	private final boolean white;
    private boolean host;
    private boolean ready;
    private String ip;
	
	public Player(String name, boolean white)
	{
		this.name = name;
		this.white = white;
		this.ready = false;
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

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getIp()
	{
		return this.ip;
	}

	public void setHost()
	{
		this.host = true;
	}

	public String getName()
	{
		return this.name;
	}

	public void setReady(boolean ready)
    {
        this.ready = ready;
    }
}
