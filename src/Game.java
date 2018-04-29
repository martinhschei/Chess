import java.util.*;
import java.util.List;

class Game extends HasListeners implements IsListener, IsActionListener, IsMoveListener, IsGame {

    private final Player player;
    private boolean movesAllowed;
    private List<Move> moves;

    private ChessBoard board;
    
	public Game(Player player)
	{
	    this.board = new ChessBoard(this);
        this.board.addListener(this);

        this.player = player;

		this.movesAllowed = this.player.isHost();
		
		this.moves = new ArrayList<>();

        Stockfish stockFish = new Stockfish();
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
        this.movesAllowed = false;
        this.publishAction(new Action("move", move));
    }

	public void newAction(Action action)
    {
        switch(action.getType()) {
            case("move") : {
                Move move = (Move)action.getPayload();
                System.out.println("Mottaker:" + this.player.getName());
                System.out.println("Host:" + this.player.isHost());
                System.out.println("---");
                this.movesAllowed = true;
                this.board.movePiece(move.getPiece(), move.getFrom(), move.getTo(), true);

            }
        }
    }
}
