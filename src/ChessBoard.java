import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

class ChessBoard extends HasActionListeners implements IsMover, IsActionListener {
	
	private final List<Row> rows = new ArrayList<>();
	private final char[] columns = new char[] { 'a','b','c','d','e','f','g','h' };
    private final Stockfish stockFish;
    private final List<Move> moves;
    private final Player player;
    private NetworkServer server;
    private NetworkClient client;

    private JFrame boardWindow;

    private boolean movesAllowed;
    
	public ChessBoard(Player player)
	{
        this.player = player;
		this.movesAllowed = this.player.isHost();
		
		this.moves = new ArrayList<>();

		this.stockFish = new Stockfish();
		//this.stockFish.connect();
		//this.stockFish.startGame();

        this.buildBoard();

		if (player.isHost()) {
		    this.server = new NetworkServer(1337);
		    this.server.addListener(this);
		    this.addListener(server);
			(new Thread(server)).start();			
		} else {
		    this.client = new NetworkClient("localhost",1337);
            this.client.addListener(this);
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
                this.move(move.getPiece(), move.getFrom(), move.getTo());
            }
        }
    }

	public void move(ChessPiece piece, Field from, Field to)
    {
        if (to.hasPiece()) {
            if (to.isCurrentPieceWhite() == piece.isWhite()) {
                return;
            }
        }
        from.setPiece(null);
        to.setPiece(piece);
        Move newMove = new Move(from, to, piece);
        this.moves.add(newMove);
        // refactoring => send med move string ved hver kommando til Stockfish
        this.stockFish.setMovesString(this.getMovesString());
        // this.movesAllowed = !this.movesAllowed;
        this.publishAction(new Action("move", newMove, this.player));
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
