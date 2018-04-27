import java.io.Serializable;

public abstract class ChessPiece implements Serializable {
	
	final String name;
	final boolean isStriking;
	final boolean white;
	
	ChessPiece(String name, boolean white)
	{
		this.name = white ? name.toUpperCase() : name;
		this.white = white;
		this.isStriking = false;
	}
	
	public boolean isWhite()
	{
		return this.white;
	}

	public abstract boolean isLegalMove(Field oldField, Field newField);

}

