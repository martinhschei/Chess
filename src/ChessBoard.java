import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends HasListeners implements IsMover {

    private IsGame game;
    private final char[] columns = new char[] { 'a','b','c','d','e','f','g','h' };
    private List<Row> rows;

    private JFrame board;

    public ChessBoard(IsGame game)
    {
        this.game = game;
        this.rows = new ArrayList();

        this.buildBoard();
        this.setStartUpPosition();

        /*Stockfish stockFish = new Stockfish();
        (new Thread(stockFish)).start();*/

    }

    private void updateBoardStatus()
    {
        if (this.game.myTurn()) {
            this.board.setTitle("Ditt trekk");
        } else {
            this.board.setTitle("Venter på den andre spilleren...");
        }
    }

    public void movePiece(ChessPiece piece, Field from, Field to, boolean otherPlayer)
    {
        if(otherPlayer)
        {
            from = translateToLocalField(from);
            to = translateToLocalField(to);
        }
        from.setPiece(null);
        to.setPiece(piece);
        Move newMove = new Move(from, to, piece);

        if(!otherPlayer) {
            this.publishNewMove(newMove);
        }

        this.updateBoardStatus();

        // refactoring => send med move string ved hver kommando til Stockfish
        // this.stockFish.setMovesString(this.getMovesString());

        this.board.repaint();
        this.board.revalidate();
    }

    public void buildBoard()
    {
        this.board = new JFrame();
        this.board.setSize(1000,750);
        this.board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.board.setTitle("LotionChess: Din smootheste sjakk-opplevelse!");

        GridLayout board = new GridLayout(8, 8, 1, 1);
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(board);

        JPanel leftPanel = new JPanel();
        GridLayout logLayout = new GridLayout(0,1);
        JTextArea logArea = new JTextArea("Her vil loggen printes");
        logArea.setEditable(false);

        JPanel chatArea = new JPanel();
        JPanel chatTextAreaPanel = new JPanel();
        JPanel chatTextFieldPanel = new JPanel();
        JTextArea chatTextArea = new JTextArea("Chat chat chat chat chat chat chat chat");
        JTextField chatTextField = new JTextField(20);
        chatTextAreaPanel.add(chatTextArea);
        chatTextAreaPanel.setLayout(new FlowLayout());
        chatTextFieldPanel.add(chatTextField);
        chatTextFieldPanel.setLayout(new FlowLayout());
        JSplitPane chatAreaSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chatTextAreaPanel, chatTextFieldPanel);
        chatAreaSplitpane.setDividerLocation(200);
        chatArea.add(chatAreaSplitpane);

        leftPanel.setLayout(logLayout);
        leftPanel.add(logArea);
        JSplitPane leftBoxSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logArea, chatArea);
        leftBoxSplitpane.setDividerLocation(400);
        leftBoxSplitpane.setDividerSize(2);

        leftPanel.add(leftBoxSplitpane);


        JSplitPane containerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, leftPanel);
        containerSplitPane.setOneTouchExpandable(true);
        containerSplitPane.setDividerLocation(735);
        Container content = this.board.getContentPane();
        content.add(containerSplitPane);

        for(int i = 8; i > 0; i--) {
            Row newRow = new Row(i);
            for (char column : columns) {
                Field field = new Field(new Position(column,i), this);
                newRow.addField(field);
                boardPanel.add(field.getButton());
            }
            this.rows.add(newRow);
        }
        //this.board.pack();
        this.board.repaint();
        this.board.revalidate();
        this.board.setVisible(true);

    }

    private void setStartUpPosition()
    {
        for(char column : columns) {
            this.placePieceOnPosition(new Pawn(true), new Position(column, 2));
            this.placePieceOnPosition(new Pawn(false), new Position(column, 7));

            switch (column) {
                case ('a'):
                case ('h'): {
                    this.placePieceOnPosition(new Rook(false), new Position(column, 8));
                    this.placePieceOnPosition(new Rook(true), new Position(column, 1));
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

    private Field translateToLocalField(Field field)
    {
        Row rowTo = this.getRowByIndex(field.getRow());
        return rowTo.getField(field.getColumn());
    }

    public boolean clickAllowed(Field field)
    {
        if (field.hasPiece()) {
            return (this.game.myTurn() && (field.getCurrentPiece().isWhite() == this.game.amIWhite()));
        } else {
            return this.game.myTurn();
        }
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

    protected String getCurrentFen()
    {
        StringBuilder fen = new StringBuilder();
        for(Row row : rows)
        {
            fen.append(row.getFen());
        }
        return fen.toString();
    }
}
