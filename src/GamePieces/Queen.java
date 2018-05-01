package GamePieces;

import GamePieces.ChessPiece;

public class Queen extends ChessPiece {

	public Queen(boolean white) {
		super("q",white);
		if(this.isWhite()) {
			setImagePath("Chess_qlt60.png");
		}
		else {
			setImagePath("Chess_qdt60.png");
		}
	}

}
