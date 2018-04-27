<<<<<<< HEAD
import javax.swing.JPanel;

public abstract class ChessPiece extends JPanel{
	
	final String name;
	final boolean isStriking;
	final boolean isWhite;
	protected String imgString;
	protected String imgPath = System.getProperty("user.dir") + "\\img\\";

=======
import java.io.Serializable;

public abstract class ChessPiece implements Serializable {
	
	final String name;
	final boolean isStriking;
	final boolean white;
	
>>>>>>> 5c538fc0de88080c91ab526ef81ffc8755f2b678
	ChessPiece(String name, boolean white)
	{
		this.name = white ? name.toUpperCase() : name;
		this.white = white;
		this.isStriking = false;
		this.imgString = imgPath;
	}
	
<<<<<<< HEAD
	
	public boolean white()
=======
	public boolean isWhite()
>>>>>>> 5c538fc0de88080c91ab526ef81ffc8755f2b678
	{
		return this.white;
	}
<<<<<<< HEAD
	
=======

>>>>>>> 5c538fc0de88080c91ab526ef81ffc8755f2b678
	public abstract boolean isLegalMove(Field oldField, Field newField);

}

