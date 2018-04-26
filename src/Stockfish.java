import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class Stockfish  {

	private final String stockfishPath = System.getProperty("user.dir") + "\\stockfish-9-win\\Windows\\stockfish_9_x32.exe";
	private final Stack<String> commandHistory;
	private String movesString;
	private OutputStreamWriter stockFishWriter;
	private BufferedReader stockFishReader;

    private String currentFen;
    private int fullMoveCount = 0;
    private char turn = 'w';
    private String castling = "KQkq";
	
	public Stockfish()
	{
	    this.movesString = "";
	    this.commandHistory = new Stack<>();
	}
	
	public void connect()
	{
		Runtime rt = Runtime.getRuntime();
		Process proc = null;
		try {
			proc = rt.exec(this.stockfishPath);
			this.stockFishWriter = new OutputStreamWriter(proc.getOutputStream());
			this.stockFishReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			this.setUci();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMovesString(String moves)
    {
        this.movesString = moves;
    }

	private void setUci()
	{
		this.sendCommand(StockfishCommands.SET_UCI, StockfishReturns.UCI_OK);
	}

	public void startGame()
	{
		this.sendCommand(StockfishCommands.START_NEW_GAME, StockfishReturns.READY_OK);
	}

	public void getComputerMove()
    {
        this.sendCommand(StockfishCommands.NEXT_MOVE + " " + this.getMovesHistory(), StockfishReturns.BESTMOVE);
    }

	private void readResponse(String stopMark)
	{
		String temp = "";
		try {
		    do {
                temp = this.stockFishReader.readLine();
            } while(!temp.startsWith(stopMark));
		}
		catch(Exception e) {
            e.printStackTrace();
		}
	}

	private void sendCommand(String command, String stopMark)
	{
		try 
		{
		    this.commandHistory.push(command);
			this.stockFishWriter.write(command);
			this.stockFishWriter.flush();
			this.readResponse(stopMark);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMovesHistory()
    {
        String moves = "";
        return moves;
    }
}
