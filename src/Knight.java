
public class Knight extends ChessPiece {

	public Knight(boolean white) {
		super("n", white);
		this.imgString += this.white() ? "Chess_nlt60.png" : "Chess_ndt60.png";
	}
	
	@Override
	public boolean isLegalMove(Field newField, Field oldField) {
		// TODO Auto-generated method stub
		return false;
	}
}
