import java.util.*;
import java.util.List;

class Game extends HasListeners implements IsListener, IsActionListener, IsMoveListener, IsGame {

    private Player player;
    private Player opponent;

    private boolean movesAllowed;
    private List<Move> moves;
    private ChessBoard board;
    private Stockfish stockFish;

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

    public void newMove(Move move)
    {
        moves.add(move);
        this.movesAllowed = false;
        this.publishAction(new Action("move", move));
        getBestMove();
    }

    public void getBestMove()
    {
        String answer = buildCurrentFen();
        System.out.println("FEN position: " + answer);
        stockFish.setFEN(answer);
        String moveHistory = getMovesString();
        System.out.println("Spør stockfish om bestmove");
        stockFish.setMovesString(moveHistory);
        answer = this.stockFish.getComputerMoveByFen();
        System.out.println("Stockfish svarte: " + answer);

    }

    private String buildCurrentFen(){
        StringBuilder answer = new StringBuilder();
        answer.append(this.board.getCurrentFen());
        answer.append(" " + getCurrentPlayerColor());
        answer.append(" - - 0 ");
        answer.append(getMoveCount());
        return answer.toString();
    }

    private String getCurrentPlayerColor()
    {
        String temp = "";
        if(this.myTurn())
        {
            temp = this.amIWhite() ? "w" : "b" ;

        }
        else{
            temp = this.amIWhite() ? "b" : "w" ;
        }
        return temp;
    }

    private String getMoveCount(){
	    String temp = "";
	    int no = 0;
	    no = (moves.size()/2)+1;
	    temp = Integer.toString(no);
	    return temp;
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
                this.movesAllowed = true;
                this.board.movePiece(move.getPiece(), move.getFrom(), move.getTo(), true);
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
