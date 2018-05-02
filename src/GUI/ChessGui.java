package GUI;

import GamePieces.*;
import Helpers.*;
import Interfaces.*;
import Listeners.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChessGui extends HasListeners implements IsMover, IsListener, IsLogListener {
    
    private JTextArea logArea;
    private final IsGame game;
    private final char[] columns = new char[] { 'a','b','c','d','e','f','g','h' };
    private final List<Row> rows;
    private JFrame board;
    private final Player player;
    private final JButton asksStockfishButton = new JButton("Ask Stockfish");

    public ChessGui(IsGame game)
    {
        this.game = game;
        player = this.game.getPlayer();
        this.rows = new ArrayList();
        this.buildBoard();
        this.setStartUpPosition();
        this.updateBoardStatus();
    }

    private void updateBoardStatus()
    {
        if (this.game.myTurn()) {
            this.board.setTitle("Your turn " + player.getName() + game.returnPlayerColor(player));
        } else {
            this.board.setTitle("Waiting for opponent...");
        }
    }

    private void clearHighlights()
    {
        for(Row row : rows)
        {
            for (Field field : row.getFields())
            {
                if(field.isFieldHighlighted())
                {
                    field.clearHighlights();
                }
            }
        }
    }

    public void movePiece(ChessPiece piece, Field from, Field to, boolean otherPlayer)
    {
        if(otherPlayer)
        {
            from = translateToLocalField(from);
            to = translateToLocalField(to);
            from.setPiece(null);
            to.setPiece(null);
            to.setPiece(piece);
            asksStockfishButton.setEnabled(true);
        }

        Move newMove = new Move(from, to, piece);

        if(!otherPlayer) {
            if(this.game.isMoveLegal(newMove)) {
                if(this.game.checkForOpponent())
                {
                    asksStockfishButton.setEnabled(false);
                    clearHighlights();
                    from.setPiece(null);
                    to.setPiece(null);
                    to.setPiece(piece);
                    this.publishNewMove(newMove);
                }
                else
                {
                    from.reset();
                }
            }
            else {
                from.reset();
                onNewLogEntry(
                        this.game.logIllegalMove(
                                player.getName()+this.game.returnPlayerColor(player), newMove.toString()
                        )
                );
            }
        }

        this.updateBoardStatus();
        this.board.repaint();
        this.board.revalidate();
    }

    private void buildBoard()
    {
        //Container for sjakkbrett og høyrepanel
        this.board = new JFrame();
        this.board.setSize(1000,750);
        this.board.setResizable(true);
        this.board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Container for board
        JPanel boardPanel = new JPanel();
        GridLayout boardLayout = new GridLayout(8, 8, 1, 1);
        boardPanel.setLayout(boardLayout);

        //Container for the right-box
        JPanel rightPanel = new JPanel();
        GridLayout logLayout = new GridLayout(0,1);
        rightPanel.setLayout(logLayout);
        logArea = new JTextArea("Chat and move-log:", 0, 30);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setEditable(false);

        //Container for chat box
        JPanel chatArea = new JPanel();
        GridLayout chatAreaLayout = new GridLayout(3,0);
        chatArea.setLayout(chatAreaLayout);

        //Label over chatTextField
        JTextField chatLabel = new JTextField(game.getPlayer().getName()+ ", chat with opponent!");
        chatLabel.setEditable(false);

        //Textfield for chat
        JTextField chatTextField = new JTextField(20);
        chatTextField.setPreferredSize(new Dimension(30, 10));

        //Listener for enter-click on chatTextfield
        chatTextField.addActionListener(e -> {
            if(chatTextField.getText().length() > 0 && game.checkForOpponent()) {
                sendNewChatMessage(chatTextField.getText());
                chatTextField.setText(" ");
            }
        });

        //Button for sending chat
        JButton sendChatButton = new JButton("Send");
        sendChatButton.addActionListener(e -> {
            if(chatTextField.getText().length() > 0 && game.checkForOpponent()) {
                sendNewChatMessage(chatTextField.getText());
                chatTextField.setText(" ");
            }
        });

        // Button for asking stockfish for help
        //if(game.myTurn()) { asksStockfishButton.setEnabled(true); }
        asksStockfishButton.addActionListener(e -> {
            if(game.checkForOpponent())
            {
                game.askStockFishBestMove();
                asksStockfishButton.setEnabled(false);
            }
        });

        //Panel for Send and Ask Stockfish - buttons
        JPanel chatButtonsPanel = new JPanel();

        //Building the chatArea
        chatButtonsPanel.add(sendChatButton);
        chatButtonsPanel.add(asksStockfishButton);
        chatArea.add(chatLabel);
        chatArea.add(chatTextField);
        chatArea.add(chatButtonsPanel);

        //Scrollpane for logArea
        logArea.setCaretPosition(logArea.getDocument().getLength());
        JScrollPane logScrollPane = new JScrollPane(logArea);

        //Splitpane for the right-box
        JSplitPane leftBoxSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logScrollPane, chatArea);
        leftBoxSplitpane.setEnabled(false);
        leftBoxSplitpane.setDividerLocation(570);
        leftBoxSplitpane.setDividerSize(10);
        rightPanel.add(leftBoxSplitpane);

        //Splitpane for the whole window
        JSplitPane containerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, rightPanel);
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

        this.board.repaint();
        this.board.revalidate();
        this.board.setVisible(true);
    }

    private void sendNewChatMessage(String message)
    {
        if (message.length() > 0 ) {
            Log log = new Log(LogType.CHAT, player.getName()+this.game.returnPlayerColor(player), message);
            this.onNewLogEntry(log);
            publishNewChatMessage(log);
        }
    }

    public void onNewLogEntry(Log log) {
        logArea.append("\n" +log.getPlayerName() + ": " + log.getMessage());
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
            // Gammel kode, incase jeg tuller det til
            //return (this.game.myTurn() && (field.getCurrentPiece().isWhite() == this.game.amIWhite()));
            if (this.game.myTurn() && (field.getCurrentPiece().isWhite() == this.game.amIWhite()))
            {
                return true;
            }
            return this.game.myTurn() && (field.getCurrentPiece().isWhite() != this.game.amIWhite()) && Field.selectedField != null;

        }
        else {
            return this.game.myTurn();
        }
    }

    public Field getFieldOnPosition(Position pos)
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

    public String getCurrentFen()
    {
        StringBuilder fen = new StringBuilder();
        for(Row row : rows)
        {
            fen.append(row.getFen());
        }
        return fen.toString();
    }
}
