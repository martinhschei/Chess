import javax.swing.JPanel;

public abstract class ChessPiece extends JPanel{
	
	final String name;
	final boolean isStriking;
	final boolean isWhite;
	protected String imgString;
	protected String imgPath = System.getProperty("user.dir") + "\\img\\";

	ChessPiece(String name, boolean white)
	{
		this.name = white ? name.toUpperCase() : name;
		this.isWhite = white;
		this.isStriking = false;
		this.imgString = imgPath;
	}
	
	
	public boolean white()
	{
		return this.isWhite;
	}
	
	public abstract boolean isLegalMove(Field oldField, Field newField);

}

