package GamePieces;

import GamePieces.ChessPiece;

public class King extends ChessPiece {

	public King(boolean white) {
		super("k", white);
		if(this.isWhite()) {
			setImagePath("Chess_klt60.png");
		}
		else {
			setImagePath("Chess_kdt60.png");
		}
	}

}
