
public class Bishop extends ChessPiece {
	
	public Bishop(boolean white) {
		super("b", white);
		this.imgString += this.isWhite() ? "Chess_blt60.png" : "Chess_bdt60.png";
	}

}
