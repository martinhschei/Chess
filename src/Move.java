import java.io.Serializable;

class Move implements Serializable {

    private final Field from;
    private final Field to;
    private final ChessPiece piece;

    public Move(Field from, Field to, ChessPiece piece)
    {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public String getType()
    {
        return "move";
    }

    public ChessPiece getPiece()
    {
    	return this.piece;
    }
    
    public Field getFrom()
    {
    	return this.from;
    }
    
    public Field getTo()
    {
    	return this.to;
    }

    public String toString()
    {
        return this.from.toString() + this.to.toString();
    }
}
