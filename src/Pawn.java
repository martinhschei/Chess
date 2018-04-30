
public class Pawn extends ChessPiece
{
	public Pawn(boolean white) {
		super("p", white);
		this.imgString += this.isWhite() ? "Chess_plt60.png" : "Chess_pdt60.png";
	}

}