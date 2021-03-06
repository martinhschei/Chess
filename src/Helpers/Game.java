package Helpers;

import java.util.*;

import GUI.*;
import Interfaces.*;
import Listeners.*;
import Stockfish.*;

public class Game extends HasListeners implements IsListener, IsActionListener, IsMoveListener, IsLogListener, IsGame {

    private final Logger logger;
    private final Player player;
    private Player opponent;

    private boolean movesAllowed;

    private final List<Move> moves;
    private final ChessGui chessGui;
    private final Stockfish stockFish;

    public Player getPlayer() {
        return this.player;
    }

	public Game(Player player)
	{
        this.player = player;

        logger = new Logger();

        createSocket();

        this.chessGui = new ChessGui(this);
        this.chessGui.addListener(this);
        this.addListener(chessGui);

		this.movesAllowed = this.player.isHost();

		this.moves = new ArrayList<>();

		stockFish = new Stockfish();
        (new Thread(stockFish)).start();


	}
	private void createSocket()
    {
        if (player.isHost()) {
            this.movesAllowed = true;
            NetworkServer server = new NetworkServer(80);
            server.addListener(this);
            this.addListener(server);
            (new Thread(server)).start();

        } else {
            this.movesAllowed = false;
            NetworkClient client = new NetworkClient(player.getIp(), 80);
            client.addListener(this);
            this.addListener(client);
            (new Thread(client)).start();
        }
    }

    private String getMovesString()
    {
        StringBuilder movesString = new StringBuilder();
        for(Move move : moves) {
            movesString.append(move.toString()).append(" ");
        }
        return movesString.toString();
    }
    public boolean checkForOpponent()
    {
        if(this.opponent == null)
        {
            this.publishNewChatMessage(
                    this.logger.setError(
                            this.player.getName()+returnPlayerColor(player),
                            "Missing opponent, cannot perform action yet.")
                    );
            return false;
        }
        return true;
    }

	public boolean myTurn()
    {
        return movesAllowed;
    }

    public boolean amIWhite()
    {
        return this.player.isWhite();
    }

    public boolean isMoveLegal(Move move)
    {
        String moveString = getMovesString();
        return stockFish.isMoveLegal(moveString, move);
    }

    public Log logIllegalMove(String playerName, String message)
    {
        Log temp = logger.setIllegalMove(playerName, message);
        this.publishAction(new Action("chat", temp));
        return temp;
    }

    public void onNewMove(Move move)
    {
        moves.add(move);
        this.movesAllowed = false;
        Log log = logger.setMoveLog(player.getName()+returnPlayerColor(player), move.toString());
        chessGui.onNewLogEntry(log);
        this.publishAction(new Action("move", move));
    }
    public String returnPlayerColor(Player player)
    {
        String string ="(Black)";
        if (player.isWhite())
        {
            string = "(White)";
        }
        return string;
    }

    public void onNewLogEntry(Log log)
    {
        logger.setChatLog(log.getPlayerName(), log.getMessage());
        this.publishAction(new Action("chat", log));
    }

    private void highlightMove(Move move)
    {
        move.getFrom().highlight();
        move.getTo().highlight();
    }

    private Move getBestMoveFromStockfish()
    {
        String fen = buildCurrentFen();
        stockFish.setFEN(fen);
        String moveString = this.stockFish.getComputerMoveByFen();
        return this.translateFromMoveString(moveString);
    }

    private Move translateFromMoveString(String moveString)
    {
        String[] temp = moveString.split("\\s");
        String pos1 = temp[1].substring(0,2);
        String pos2 = temp[1].substring(2,4);
        Field field1 = translateToField(pos1);
        Field field2 = translateToField(pos2);
        return new Move(field1,field2,null);
    }

    private char translatePositionToChar (String position)
    {
        return position.substring(0,1).toCharArray()[0];
    }
    private int translatePositionToInt (String position)
    {
        return Integer.parseInt(position.substring(1,2));
    }

    private Field translateToField(String position)
    {
        char c = this.translatePositionToChar(position);
        int i = this.translatePositionToInt(position);
        return this.chessGui.getFieldOnPosition(new Position(c,i));
    }

    private String buildCurrentFen(){
        return this.chessGui.getCurrentFen() +
                " " + this.getPlayerColorForUserWhoHaveTurn() +
                " - - 0 " +
                getMoveCount();
    }
    
    private String getPlayerColorForUserWhoHaveTurn()
    {
        if(this.myTurn()) {
            return this.amIWhite() ? "w" : "b" ;
        }
        return this.amIWhite() ? "b" : "w" ;
    }

    private String getMoveCount()
    {
	    return Integer.toString((moves.size()/2)+1);
    }

    private void onNewOpponentMove(Move move)
    {
        this.movesAllowed = true;
        this.chessGui.movePiece(move.getPiece(), move.getFrom(), move.getTo(), true);
    }
    public void askStockFishBestMove()
    {
        if(this.movesAllowed)
        {
            Move move = getBestMoveFromStockfish();
            this.highlightMove(move);
            Log log = logger.setStockfishLog(player.getName()+returnPlayerColor(player), " Asked stockfish! (" + move.toString() +")");
            this.publishNewChatMessage(log);
            this.publishAction(new Action("chat", log));
        }
        else
        {
            Log log = logger.setStockfishLog(player.getName()+returnPlayerColor(player), "You can't ask Stockfish when its not your move!");
            this.publishNewChatMessage(log);
        }
    }

	public void onNewAction(Action action)
    {
        switch(action.getType()) {
            case("move") : {
                Move move = (Move)action.getPayload();
                moves.add(move);
                Log log = logger.setMoveLog(opponent.getName()+returnPlayerColor(opponent), move.toString());
                chessGui.onNewLogEntry(log);
                this.onNewOpponentMove(move);
                break;
            }
            case("hereiam") : {
                // server replies
                this.publishAction(new Action("whoareyou", null));
                break;
            }

            case("whoareyou") : {
                // client replies
                this.publishAction(new Action("thisisme", this.player));
                break;
            }

            case("thisisme") : {
                if (this.opponent == null) {
                    this.opponent = (Player)action.getPayload();
                    if (this.player.isHost()) {
                        this.publishAction(new Action("thisisme", this.player));
                    }
                }
                break;
            }

            case("chat") : {
                Log chat = (Log)action.getPayload();
                logger.setChatLog(opponent.getName(), chat.getMessage());
                publishNewChatMessage(chat);
                break;
            }
        }
    }
}
