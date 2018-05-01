package GamePieces;

public class Bishop extends ChessPiece {
	
	public Bishop(boolean white) {
		super("b", white);
		if(this.isWhite()) {
			setImagePath("Chess_blt60.png");
		}
		else {
			setImagePath("Chess_bdt60.png");
		}
	}
}
