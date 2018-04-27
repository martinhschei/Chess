import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.Serializable;
import javax.swing.border.LineBorder;


public class Field implements Serializable {
	
	private ChessPiece currentPiece;
	private JButton button;
	final Position position;
	private boolean selected;
	private transient IsMover mover;
	public static Field selectedField;
	
	public JLabel Image() {
		
	    BufferedImage img = null;
		
		
		try {
			img = ImageIO.read(new File(this.currentPiece.imgString));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel imgLabel = new JLabel(new ImageIcon(img));
		
		return imgLabel;
		
		}

	public Field(Position position, IsMover mover)
	{
		this.position = position;
		this.currentPiece = null;
		this.selected = false;
		this.mover = mover;
		this.createFieldButton();
	}
	
	private boolean isFieldWhite()
	{
		if (this.position.getRow() % 2 == 0) {
			return this.position.getColumn() == 'a' || this.position.getColumn() == 'c' || this.position.getColumn() == 'e' || this.position.getColumn() == 'g';
		} else {
			return this.position.getColumn() == 'b' || this.position.getColumn() == 'd' || this.position.getColumn() == 'f' || this.position.getColumn() == 'h';
		}
	}

	public void clearSelection()
    {
        this.button.setBorderPainted(false);
        this.button.setText(this.position.getColumn() + String.valueOf(this.position.getRow()));
        this.button.getComponent(0).setVisible(false);
        
    }

	private void createFieldButton()
	{
		this.button = this.currentPiece == null ? new JButton(this.position.getColumn() + String.valueOf(this.position.getRow())) : new JButton(this.currentPiece.name);
		this.button.setBorderPainted(false);
		this.button.setFocusPainted(false);
		this.button.setContentAreaFilled(true);
		
		if (this.isFieldWhite()) {
			this.button.setBackground(Color.WHITE);
			this.button.setForeground(Color.GRAY);
		}
		else {
			this.button.setBackground(Color.GRAY);
			this.button.setForeground(Color.WHITE);
		}

		this.button.addActionListener(e -> this.fieldClick() );
    }

	private void fieldClick()
	{
		// deselect
		if (Field.selectedField == this) {
			this.button.setBorderPainted(false);
			this.selected = false;
			Field.selectedField = null;
			return;
		}

		// select
		if (!this.selected && this.currentPiece != null && Field.selectedField == null) {
			this.button.setBorderPainted(true);
			this.button.setBorder(new LineBorder(Color.CYAN));
			this.button.repaint();
			this.selected = true;
			Field.selectedField = this;
			return;
		}

		// move
		if (!this.selected && Field.selectedField != null && Field.selectedField != this) {
			this.mover.move(Field.selectedField.currentPiece, Field.selectedField, this, false);
			this.selected = false;
			Field.selectedField.clearSelection();
			Field.selectedField.selected = false;
			Field.selectedField = null;
			return;
		}
	}
	
	public boolean isCurrentPieceWhite()
	{
		if (this.currentPiece != null) {
			return this.currentPiece.isWhite();
		}
		return false;
	}
	
	public boolean isAtPosition(Position pos)
	{
		return (this.position.getRow() == pos.getRow() && this.position.getColumn() == pos.getColumn());
	}
	
	public void setPiece(ChessPiece piece)
	{
		
		this.currentPiece = piece;
		if (piece == null) {
		    this.button.setText(this.position.getColumn() + String.valueOf(this.position.getRow()));
        } else {
            this.button.setText(piece.name);
    		this.button.add(Image());;
        }

		this.button.repaint();
	}

	public String getCurrentPieceName()
	{
		if (this.hasPiece()) {
			return this.currentPiece.name;
		}
		return null;
	}

	public boolean hasPiece() {
		return this.currentPiece != null;
	}

	private int getRow()
	{
		return this.position.getRow();
	}

	public char getColumn()
	{
		return this.position.getColumn();
	}
	
	public JButton getButton()
	{
		return this.button;
	}

	public Position getPosition() {
		return position;
	}

	public ChessPiece getCurrentPiece() {
		return currentPiece;
	}

    public String toString() {
        return this.getColumn() + Integer.toString(this.getRow());
    }
}