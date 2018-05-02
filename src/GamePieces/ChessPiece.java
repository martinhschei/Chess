package GamePieces;

import javax.swing.JPanel;

public abstract class ChessPiece extends JPanel {
	
	private final String name;
	private final boolean white;
	private String imagePath;

	ChessPiece(String name, boolean white)
	{
		this.name = white ? name.toUpperCase() : name;
		this.white = white;
	}

	public boolean isWhite()

	{
		return this.white;
	}
	public String getImagePath() { return imagePath; }
	void setImagePath(String path) { imagePath = path; }
	public String getName() { return name; }
}

