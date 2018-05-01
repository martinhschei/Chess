
public class Rook extends ChessPiece {

	public Rook(boolean white) {
		super("r", white);
		this.imgString += this.isWhite() ? "Chess_rlt60.png" : "Chess_rdt60.png";
	}

}
