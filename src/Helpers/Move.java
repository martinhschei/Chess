package Helpers;

import GamePieces.ChessPiece;
import Helpers.Field;

import java.io.Serializable;

public class Move implements Serializable {

    private Field from;
    private Field to;
    private ChessPiece piece;

    public Move(Field from, Field to, ChessPiece piece)
    {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public Move() { }

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
