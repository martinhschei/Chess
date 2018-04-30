
public class Rook extends ChessPiece {

	public Rook(boolean white) {
		super("r", white);
		this.imgString += this.isWhite() ? "Chess_rlt60.png" : "Chess_rdt60.png";
	}

	@Override
	public boolean isLegalMove(Field newField, Field oldField) {
		// TODO Auto-generated method stub
		return false;
	}
}
