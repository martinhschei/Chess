
public class Queen extends ChessPiece {

	public Queen(boolean white) {
		super("q",white);
		this.imgString += this.isWhite() ? "Chess_qlt60.png" : "Chess_qdt60.png";
	}

	@Override
	public boolean isLegalMove(Field newField, Field oldField) {
		// TODO Auto-generated method stub
		return false;
	}

}
