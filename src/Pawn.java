
public class Pawn extends ChessPiece
{
	public Pawn(boolean white) {
		super("p", white);
		this.imgString += this.isWhite() ? "Chess_plt60.png" : "Chess_pdt60.png";
	}
	
	@Override
	public boolean isLegalMove(Field newField, Field oldField) {
		
		Position newPos = newField.position;
		Position oldPos = oldField.position;
		
		// can not move backwards
		if (newPos.getRow() < oldPos.getRow()) {
			return false;
		}
		// can only move one row at the time
		if (newPos.getRow() >  oldPos.getRow() + 1) {
			return false;
		}
		// can not move sideways on a row
		if (newPos.getRow() == oldPos.getRow()) {
			return false;
		}
		// can not move into another column if not striking another piece
        return newPos.getColumn() == oldPos.getColumn() || this.isStriking;
    }
}