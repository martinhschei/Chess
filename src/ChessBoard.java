import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

class ChessBoard extends HasActionListeners implements IsMover, IsActionListener {
	
	private final List<Row> rows = new ArrayList<>();
	private final char[] columns = new char[] { 'a','b','c','d','e','f','g','h' };
    private final List<Move> moves;
    private final Player player;
    private JFrame boardWindow;
    private boolean movesAllowed;
    
	public ChessBoard(Player player)
	{
        this.player = player;

		this.movesAllowed = this.player.isHost();
		
		this.moves = new ArrayList<>();

        Stockfish stockFish = new Stockfish();
        (new Thread(stockFish)).start();

        this.buildBoard();
        this.updateBoardStatus();

		if (player.isHost()) {
            NetworkServer server = new NetworkServer(1337);
		    server.addListener(this);
		    this.addListener(server);
			(new Thread(server)).start();			
		} else {
            NetworkClient client = new NetworkClient("localhost", 1337);
            client.addListener(this);
            this.addListener(client);
			(new Thread(client)).start();
		}
	}

	public void newAction(Action action)
    {
        switch(action.getType()) {
            case("move") : {
                Move move = (Move)action.getPayload();
                System.out.println("Avsender:" + action.getPlayer().getName());
                System.out.println("Mottaker:" + this.player.getName());
                System.out.println("Host:" + this.player.isHost());
                System.out.println("---");
                this.move(move.getPiece(), this.translateToLocalField(move.getFrom()), this.translateToLocalField(move.getTo()), true);
            }
        }
    }

    private Field translateToLocalField(Field field)
    {
        Row rowTo = this.getRowByIndex(field.getRow());
        return rowTo.getField(field.getColumn());
    }

    public boolean clickAllowed(Field field) {
	    if (field.hasPiece()) {
            return (this.movesAllowed && (field.getCurrentPiece().isWhite() == this.player.isWhite()));
        } else {
	        return this.movesAllowed;
        }
    }

    private void updateBoardStatus()
    {
        if (this.movesAllowed) {
            this.boardWindow.setTitle("Ditt trekk");
        } else {
            this.boardWindow.setTitle("Venter på den andre spilleren...");
        }
    }

    public void move(ChessPiece piece, Field from, Field to, boolean otherPlayer)
    {
        from.setPiece(null);
        to.setPiece(piece);
        Move newMove = new Move(from, to, piece);
        this.moves.add(newMove);

        if(!otherPlayer) {
            this.publishAction(new Action("move", newMove, this.player));
        }

        this.movesAllowed = !this.movesAllowed;
        this.updateBoardStatus();

        // refactoring => send med move string ved hver kommando til Stockfish
        // this.stockFish.setMovesString(this.getMovesString());

        this.boardWindow.repaint();
        this.boardWindow.revalidate();
    }

    private String getMovesString()
    {
        StringBuilder movesString = new StringBuilder();
        for(Move move : moves) {
            movesString.append(move.toString()).append(" ");
        }
        return movesString.toString();
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
		this.boardWindow = new JFrame("Java Sjakk - " + this.player.getName());
        this.boardWindow.setBounds(50,50,1000,1000);
        this.boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(8, 8, 1, 1);
		Container content = boardWindow.getContentPane(); 
		content.setLayout(grid);

		for(int i = 8; i > 0; i--) {
			Row newRow = new Row(i);
			for (char column : columns) {
				Field field = new Field(new Position(column,i),this);
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
