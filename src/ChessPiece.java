import javax.swing.JPanel;

public abstract class ChessPiece extends JPanel {
	
	final String name;
	final boolean isStriking;
	private final boolean white;
	String imgString;
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

}

