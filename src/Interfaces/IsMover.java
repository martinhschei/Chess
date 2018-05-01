package Interfaces;

import GamePieces.ChessPiece;
import Helpers.*;

public interface IsMover {
    void movePiece(ChessPiece piece, Field from, Field to, boolean otherPlayer);
    boolean clickAllowed(Field field);
}
