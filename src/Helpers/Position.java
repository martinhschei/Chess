package Helpers;

import java.io.Serializable;

public class Position implements Serializable {
	
	private final int row;
	private final char column;

	public Position(char column, int row)
	{
		this.column = column;
		this.row = row;
	}

	public int getRow() {
		return this.row;
	}
	
	public char getColumn() {
		return this.column;
	}
	
}
