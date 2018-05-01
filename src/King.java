
public class King extends ChessPiece {

	public King(boolean white) {
		super("k", white);
		this.imgString += this.isWhite() ? "Chess_klt60.png" : "Chess_kdt60.png";
	}

}
