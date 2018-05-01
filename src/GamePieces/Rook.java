package GamePieces;

import GamePieces.ChessPiece;

public class Rook extends ChessPiece {

	public Rook(boolean white) {
		super("r", white);
		if(this.isWhite()) {
			setImagePath("Chess_rlt60.png");
		}
		else {
			setImagePath("Chess_rdt60.png");
		}
	}

}
