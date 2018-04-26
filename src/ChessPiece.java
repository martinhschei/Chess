public abstract class ChessPiece {
	
	final String name;
	final boolean isStriking;
	final boolean isWhite;
	
	ChessPiece(String name, boolean white)
	{
		this.name = white ? name.toUpperCase() : name;
		this.isWhite = white;
		this.isStriking = false;
	}
	
	public boolean white()
	{
		return this.isWhite;
	}
	
	
	
	public abstract boolean isLegalMove(Field oldField, Field newField);

}

