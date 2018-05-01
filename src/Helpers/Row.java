package Helpers;

import Helpers.Field;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private final List<Field> fields = new ArrayList<>();

	private final int index;
	
	public Row(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return this.index;
	}

	public List<Field> getFields()
	{
		return fields;
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
		int emptyField = 0;
		for(Field field : fields) {
			if(field.hasPiece()) {
				fen.append(emptyField == 0 ? field.getCurrentPieceName() : emptyField + field.getCurrentPieceName());
				emptyField = 0;
			}
			else {
				emptyField++;
			}
		}
		if (emptyField != 0){
			fen.append(emptyField);
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
		StringBuilder offsetString = new StringBuilder();
		for(Field field : row) {
			if (field.hasPiece()) {
				offsetString.append(emptyCol == 0 ? field.getCurrentPieceName() + "/" : (emptyCol + field.getCurrentPieceName()) + "/");
			}
			emptyCol++;
		}
		return offsetString.toString();
	}
}
