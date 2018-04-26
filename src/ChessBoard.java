import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

class ChessBoard extends Observable implements Observer {
	
	private final List<Row> rows = new ArrayList<Row>();
	private final char[] columns = new char[] { 'a','b','c','d','e','f','g','h' };
    private final Stockfish stockFish;
	private Field selectedField = null;
    private final List<Move> moves;
    private final List<Player> players;
    
	public ChessBoard()
	{
		this.buildBoard();
		
		this.moves = new ArrayList<Move>();
		this.players = new ArrayList<Player>();
		
		this.stockFish = new Stockfish();
		//this.stockFish.connect();
		//this.stockFish.startGame();
		NetworkServer server = new NetworkServer(1337);
		server.addObserver(this);
		
		(new Thread(new NetworkServer(1337))).start();
		(new Thread(new NetworkClient("localhost",1337))).start();
		
	}

	private void move(ChessPiece piece, Field from, Field to)
    {
        from.setPiece(null);
        if (to.hasPiece()) {
            if (to.isCurrentPieceWhite() == piece.isWhite) {
                return;
            } else {

            }
        }
        to.setPiece(piece);
        Move newMove = new Move(from, to, piece);
        this.moves.add(newMove);
        // refactoring => send med move string ved hver kommando til Stockfish
        this.stockFish.setMovesString(this.getMovesString());
    }

    private String getMovesString()
    {
        StringBuilder movesString = new StringBuilder();
        for(Move move : moves) {
            movesString.append(move.toString()).append(" ");
        }
        return movesString.toString();
    }
    
	public void update(Observable observable, Object object)
    {
		switch(observable.getClass().toString().substring(6))
		{
			case("Field"): {
				this.handleFieldChanges((Field)observable);
				break;
			}
			case("Action"): {
				this.handleAction((Action)observable);
				break;
			}
		}
    }
	
	private void handleAction(Action action)
	{
		switch(action.getType()) {
			case("move") : {
				Move move = (Move)action.getPayload();
				this.move(move.getPiece(), move.getFrom(), move.getTo());
			}
		}
	}
	
	private void handleFieldChanges(Field field)
	{
        JButton btn = field.getButton();

        // deselect
        if (field == this.selectedField) {
            btn.setBorderPainted(false);
            this.selectedField.clearSelection();
            this.selectedField = null;
            return;
        }
        
        // move
        if (this.selectedField != null) {
            move(this.selectedField.getCurrentPiece(),this.selectedField, field);
            this.selectedField.clearSelection();
            this.selectedField = null;
            return;
        }

        // select
        if (this.selectedField == null && field.hasPiece()) {
            btn.setBorderPainted(true);
            btn.setBorder(new LineBorder(Color.CYAN));
            btn.repaint();
            this.selectedField = field;
        }
	}

    private String getCurrentFen()
	{
		StringBuilder fen = new StringBuilder();
		for(Row row : rows)
		{
			fen.append(row.getFen());
		}
		return fen.toString();
	}

	private void buildBoard()
    {
		JFrame boardWindow = new JFrame("Java Sjakk");
		boardWindow.setBounds(50,50,1000,1000);
		boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(8, 8, 1, 1);
		Container content = boardWindow.getContentPane(); 
		content.setLayout(grid);

		for(int i = 8; i > 0; i--) {
			Row newRow = new Row(i);
			for (char column : columns) {
				Field field = new Field(new Position(column,i));
				field.addObserver(this);
				newRow.addField(field);
				content.add(field.getButton());
			}
			this.rows.add(newRow);
		}

        this.setStartUpPosition();

		boardWindow.pack();
		boardWindow.setVisible(true);
	}

	private Field getFieldOnPosition(Position pos)
    {
        Row row = this.getRowByIndex(pos.getRow());
        return row.getField(pos.getColumn());
    }

    private Row getRowByIndex(int index)
    {
        for(Row row: rows) {
            if (row.getIndex() == index) {
                return row;
            }
        }
        return null;
    }

	private void placePieceOnPosition(ChessPiece piece, Position pos)
	{
        Field field = getFieldOnPosition(pos);
        field.setPiece(piece);
	}

	private void setStartUpPosition()
	{
        for(char column : columns) {
            this.placePieceOnPosition(new Pawn(true), new Position(column, 2));
            this.placePieceOnPosition(new Pawn(false), new Position(column, 7));

            switch (column) {
                case ('a'):
                case ('h'): {
                    this.placePieceOnPosition(new Tower(false), new Position(column, 8));
                    this.placePieceOnPosition(new Tower(true), new Position(column, 1));
                    break;
                }
                case ('b'):
                case ('g'): {
                    this.placePieceOnPosition(new Knight(false), new Position(column, 8));
                    this.placePieceOnPosition(new Knight(true), new Position(column, 1));
                    break;
                }
                case ('c'):
                case ('f'): {
                    this.placePieceOnPosition(new Bishop(false), new Position(column, 8));
                    this.placePieceOnPosition(new Bishop(true), new Position(column, 1));
                    break;
                }
                case ('d'): {
                    this.placePieceOnPosition(new Queen(false), new Position(column, 8));
                    this.placePieceOnPosition(new Queen(true), new Position(column, 1));
                    break;
                }
                case ('e'): {
                    this.placePieceOnPosition(new King(false), new Position(column, 8));
                    this.placePieceOnPosition(new King(true), new Position(column, 1));
                    break;
                }
            }
        }
	}
}
