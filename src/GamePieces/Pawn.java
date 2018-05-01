package GamePieces;

import GamePieces.ChessPiece;

public class Pawn extends ChessPiece
{
	public Pawn(boolean white) {
		super("p", white);
		if(this.isWhite()) {
			setImagePath("Chess_plt60.png");
		}
		else {
			setImagePath("Chess_pdt60.png");
		}
    }

}