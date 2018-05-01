package GamePieces;

import GamePieces.ChessPiece;

public class Knight extends ChessPiece {

	public Knight(boolean white) {
		super("n", white);
		if(this.isWhite()) {
			setImagePath("Chess_nlt60.png");
		}
		else {
			setImagePath("Chess_ndt60.png");
		}
	}

}
