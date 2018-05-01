import java.util.*;

class Game extends HasListeners implements IsListener, IsActionListener, IsMoveListener, IsChatListener, IsGame {

    private Logger logger;
    private Player player;
    private Player opponent;

    private boolean movesAllowed;

    private List<Move> moves;
    private ChessGui chessGui;
    private Stockfish stockFish;

    public Player getPlayer() {
        return player;
    }

	public Game(Player player)
	{
        logger = new Logger();

        this.chessGui = new ChessGui(this);
        this.chessGui.addListener(this);

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

    public void onNewMove(Move move)
    {
        moves.add(move);
        this.movesAllowed = false;
        logger.setMoveLog(player.getName(), move.toString());
        this.publishAction(new Action("move", move));

        //TEST START =>
        List<Log> loggerList = logger.getLog();
        for (Log log : loggerList) {
            System.out.println(log.getPlayerName() + " " + log.getMessage());
        }
        //TEST SLUTT <=
    }

    public void onNewChatMessage(String message)
    {
        logger.setChatLog(player.getName(), message);
        this.publishAction(new Action("chat", new Log(LogType.CHAT, player.getName(), message)));
    }

    private void highlightMove(Move move)
    {
        move.getFrom().highlight();
        move.getTo().highlight();
    }

    private Move getBestMove()
    {
        String fen = buildCurrentFen();
        System.out.println("FEN position: " + fen);
        stockFish.setFEN(fen);
        System.out.println("Spør stockfish om bestmove");
        String moveString = this.stockFish.getComputerMoveByFen();
        System.out.println("Stockfish svarte: " + moveString);
        return this.translateFromMoveString(moveString);
    }

    private Move translateFromMoveString(String moveString)
    {
        String[] temp = moveString.split("\\s");
        String pos1 = temp[1].substring(0,2);
        System.out.println("Pos1: " + pos1);
        String pos2 = temp[1].substring(2,4);
        System.out.println("Pos2: " + pos2);
        Field field1 = translateToField(pos1);
        Field field2 = translateToField(pos2);
        return new Move(field1,field2,null);
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
        Field field = this.chessGui.getFieldOnPosition(new Position(c,i));
        return field;
    }

    private String buildCurrentFen(){
        String answer = this.chessGui.getCurrentFen() +
                " " + this.getPlayerColorForUserWhoHaveTurn() +
                " - - 0 " +
                getMoveCount();
        return answer;
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
        Move bestMove = getBestMove();
        this.highlightMove(bestMove);
    }

	public void onNewAction(Action action)
    {
        switch(action.getType()) {
            case("move") : {
                Move move = (Move)action.getPayload();
                moves.add(move);
                logger.setMoveLog(opponent.getName(), move.toString());
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
                break;
            }

            case("chat") : {
                Log chat = (Log)action.getPayload();
                logger.setChatLog(opponent.getName(), chat.getMessage());
                this.chessGui.onNewChatMessage((Log)action.getPayload());
                break;
            }
        }
    }
}
