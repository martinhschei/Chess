import javax.swing.JPanel;

public abstract class ChessPiece extends JPanel{
	
	final String name;
	final boolean isStriking;
	final boolean white;
	protected String imgString;
	//protected String imgPath = System.getProperty("user.dir") + "\\img\\";

	ChessPiece(String name, boolean white)
	{
		this.name = white ? name.toUpperCase() : name;
		this.white = white;
		this.isStriking = false;
		this.imgString = "";
	}

	public boolean isWhite()

	{
		return this.white;
	}

	public abstract boolean isLegalMove(Field oldField, Field newField);

}

