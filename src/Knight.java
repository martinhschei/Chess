
public class Knight extends ChessPiece {

	public Knight(boolean white) {
		super("n", white);
		this.imgString += this.isWhite() ? "Chess_nlt60.png" : "Chess_ndt60.png";
	}

}
