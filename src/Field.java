import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;

public class Field extends Observable {
	
	ChessPiece currentPiece;
	final Position position;
	private JButton button;

	public Field(Position position)
	{
		this.position = position;
		this.currentPiece = null;
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
    }

	private void createFieldButton()
	{
		JButton button = new JButton();
		this.button = this.currentPiece == null ? new JButton(this.position.getColumn() + String.valueOf(this.position.getRow())) : new JButton(this.currentPiece.name);
		this.button.setBorderPainted(false);
		this.button.setFocusPainted(false);
		this.button.setContentAreaFilled(true);
		if (this.isFieldWhite()) {
			this.button.setBackground(Color.WHITE);
			this.button.setForeground(Color.BLACK);
		}
		else {
			this.button.setBackground(Color.BLACK);
			this.button.setForeground(Color.WHITE);
		}
		this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setChanged();
                notifyObservers(actionEvent.getSource());
            }
        });
    }
	
	public boolean hasPiece()
	{
		return this.currentPiece != null;
	}
	
	public boolean isCurrentPieceWhite()
	{
		if (this.hasPiece()) {
			return this.currentPiece.isWhite;
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