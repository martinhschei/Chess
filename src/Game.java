import java.util.*;
import java.util.List;

class Game extends HasActionListeners implements IsActionListener, IsGame {

    private final Player player;
    private boolean movesAllowed;
    private List<Move> moves;

    private ChessBoard board;
    
	public Game(Player player)
	{
	    this.board = new ChessBoard(this);

        this.player = player;

		this.movesAllowed = this.player.isHost();
		
		this.moves = new ArrayList<>();

        Stockfish stockFish = new Stockfish();
        (new Thread(stockFish)).start();

		if (player.isHost()) {
            NetworkServer server = new NetworkServer(1337);
		    server.addActionListener(this);
		    this.addActionListener(server);
			(new Thread(server)).start();			
		} else {
            NetworkClient client = new NetworkClient("localhost", 1337);
            client.addActionListener(this);
            this.addActionListener(client);
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
        return true;
    }

    public boolean amIWhite()
    {
        return this.player.isWhite();
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
                this.board.movePiece(move.getPiece(), move.getFrom(), move.getTo(), true);
            }
        }
    }

}
