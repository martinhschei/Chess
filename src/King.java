
public class King extends ChessPiece {

	public King(boolean white) {
		super("k", white);
		this.imgString += this.white() ? "Chess_klt60.png" : "Chess_kdt60.png";
	}

	@Override
	public boolean isLegalMove(Field newField, Field oldField) {
		// TODO Auto-generated method stub
		return false;
	}

}
