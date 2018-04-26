import java.util.ArrayList;
import java.util.List;

class Row {

	private final List<Field> fields = new ArrayList<Field>();
	private final int index;
	
	public Row(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return this.index;
	}
	
	public void addField(Field field)
	{
		this.fields.add(field);
	}

	public Field getField(char column)
	{
		for(Field field : fields) {
			if (field.getColumn() == column) {
				return field;
			}
		}
		return null;
	}

	public String getFen()
	{
		if (this.rowHasZeroPieces()) {
			return "8/";
		}
		
		StringBuilder fen = new StringBuilder();
		int empty = 0;
		for(Field field : fields) {
			if(field.hasPiece()) {
				fen.append(empty == 0 ? field.getCurrentPieceName() : empty + field.getCurrentPieceName());
			}
		}
		return fen + "/";
	}
	
	private boolean rowHasZeroPieces()
	{
		for(Field field : fields) {
			if (field.hasPiece()) {
				return false;
			}
		}
		return true;
	}
	
	private String getOffsets(List<Field> row)
	{
		int emptyCol = 0;
		String offsetString = "";
		for(Field field : row) {
			if (field.hasPiece()) {
				offsetString += emptyCol == 0 ? field.currentPiece.name +"/" : (emptyCol + field.currentPiece.name) + "/";
			}
			emptyCol++;
		}
		return offsetString;
	}
}
