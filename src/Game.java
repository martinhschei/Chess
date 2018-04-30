import java.util.*;

class Game extends HasListeners implements IsListener, IsActionListener, IsMoveListener, IsGame {

    private Player player;
    private Player opponent;
    private boolean movesAllowed;
    private List<Move> moves;
    private ChessBoard board;
    private Stockfish stockFish;

    private Logger logger;

	public Game(Player player)
	{
	    this.board = new ChessBoard(this);
        this.board.addListener(this);

        this.player = player;

		this.movesAllowed = this.player.isHost();

		this.moves = new ArrayList<>();

		stockFish = new Stockfish();
        (new Thread(stockFish)).start();

        if (player.isHost()) {
            NetworkServer server = new NetworkServer(1337);
            server.addListener(this);
            this.addListener(server);
            (new Thread(server)).start();
        } else {
            NetworkClient client = new NetworkClient(player.getIp(), 1337);
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


    public void newMove(Move move)
    {
        moves.add(move);
        this.movesAllowed = false;
        this.publishAction(new Action("move", move));
    }

    private void highlightMove(Move move)
    {
        move.getFrom().highlight();
        move.getTo().highlight();
    }

    public Move getBestMove()
    {
        String fen = buildCurrentFen();
        System.out.println("FEN position: " + fen);
        stockFish.setFEN(fen);
        System.out.println("Spør stockfish om bestmove");
        String moveString = this.stockFish.getComputerMoveByFen();
        System.out.println("Stockfish svarte: " + moveString);
        return this.translateFromMoveString(moveString);
    }

    public void onBoardAction(Action action)
    {

    }

    private Move translateFromMoveString(String moveString)
    {
	    Move move = null;
        String pos1 = "";
        String pos2 = "";
        String[] temp = moveString.split("\\s");
        pos1 = temp[1].substring(0,2);
        System.out.println("Pos1: " + pos1);
        pos2 = temp[1].substring(2,4);
        System.out.println("Pos2: " + pos2);
        Field field1 = translateToField(pos1);
        Field field2 = translateToField(pos2);
        move = new Move(field1,field2,null);
	    return move;
    }

    private char translatePositionToChar (String position)
    {
        System.out.println("Debug: translatePositionToChar " + position.substring(0,1).toCharArray()[0]);
        return position.substring(0,1).toCharArray()[0];
    }
    private int translatePositionToInt (String position)
    {
        System.out.println("Debug: translatePositionToInt " + Integer.parseInt(position.substring(1,2)));
        return Integer.parseInt(position.substring(1,2));
    }

    private Field translateToField(String position)
    {
        char c = this.translatePositionToChar(position);
        int i = this.translatePositionToInt(position);
        Field field = this.board.getFieldOnPosition(new Position(c,i));
        return field;
    }

    private String buildCurrentFen(){
        StringBuilder answer = new StringBuilder();
        answer.append(this.board.getCurrentFen());
        answer.append(" " + this.getPlayerColorForUserWhoHaveTurn());
        answer.append(" - - 0 ");
        answer.append(getMoveCount());
        return answer.toString();
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
	    String temp = "";
	    int no = 0;
	    no = (moves.size()/2)+1;
	    temp = Integer.toString(no);
	    return temp;
    }

    private void onNewOpponentMove(Move move)
    {
        this.movesAllowed = true;
        this.board.movePiece(move.getPiece(), move.getFrom(), move.getTo(), true);
        Move bestMove = getBestMove();
        this.highlightMove(bestMove);
    }

	public void newAction(Action action)
    {
        switch(action.getType()) {
            case("move") : {
                Move move = (Move)action.getPayload();
                moves.add(move);
                System.out.println("Mottaker:" + this.player.getName());
                System.out.println("Host:" + this.player.isHost());
                System.out.println("---");
                this.onNewOpponentMove(move);
                break;
            }
            case("hereiam") : {
                // add to log, new player present.

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

                // for debugging
                if(this.player.isHost()) {
                    System.out.println("Host::" + this.opponent);
                }

                if(!this.player.isHost()) {
                    System.out.println("Client:" + this.opponent);
                }

                break;
            }
            case("chat") : {
                // addToLog(action.gePayload());
                break;
            }
        }
    }
}
