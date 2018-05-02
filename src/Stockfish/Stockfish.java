package Stockfish;

import Helpers.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stockfish implements Runnable {

	private final String stockfishPath = System.getProperty("user.dir") + "\\stockfish-9-win\\Windows\\stockfish_9_x32.exe";
	private String movesString;
	private OutputStreamWriter stockFishWriter;
	private BufferedReader stockFishReader;
    private String currentFen;
	
	public Stockfish()
	{
	    this.movesString = "";
	}

	public void run()
	{
		Runtime rt = Runtime.getRuntime();
		try {
			Process proc = rt.exec(this.stockfishPath);
			this.stockFishWriter = new OutputStreamWriter(proc.getOutputStream());
			this.stockFishReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			System.out.println("Stockfish waiting for work");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isMoveLegal(String movesString, Move move)
	{
		boolean answer = false;
		String moveString = move.toString();
		String fen = "";
		String newFen = "";

		try {
			this.stockFishWriter.write(StockfishCommands.START_NEW_GAME);
			this.stockFishWriter.flush();
			this.stockFishWriter.write(StockfishCommands.SET_POSITION  + movesString + "\n");
			this.stockFishWriter.flush();
			fen = this.sendCommand(StockfishCommands.GET_POSITION, StockfishReturns.FEN_POSITION);
			this.stockFishWriter.write(StockfishCommands.START_NEW_GAME);
			this.stockFishWriter.flush();
			this.stockFishWriter.write(StockfishCommands.SET_POSITION  + movesString + " " + moveString + "\n");
			this.stockFishWriter.flush();
			newFen = this.sendCommand(StockfishCommands.GET_POSITION, StockfishReturns.FEN_POSITION);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		fen = translateAnswerToFen(fen);
		newFen = translateAnswerToFen(newFen);
		if(!newFen.equals(fen)) {
			answer = true;
		}
		return answer;
	}

	private String translateAnswerToFen(String answer)
	{
		String fen;
		int length = answer.length();
		fen = answer.substring(6,(length));

		return fen;
	}


	public void setMovesString(String moves)
    {
        this.movesString = moves;
    }

	public void setFEN(String fen)
	{
		this.currentFen = fen;
	}

	private void setUci()
	{
		this.sendCommand(StockfishCommands.SET_UCI, StockfishReturns.UCI_OK);
	}

	public void startGame()
	{
		this.sendCommand(StockfishCommands.START_NEW_GAME, StockfishReturns.READY_OK);
	}

	public String getComputerMove()
	{
		try {
			this.stockFishWriter.write(StockfishCommands.SET_POSITION + this.getMovesHistory() + "\n");
			this.stockFishWriter.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this.sendCommand(StockfishCommands.NEXT_MOVE +"\n", StockfishReturns.BESTMOVE);
	}

	public String getComputerMoveByFen()
	{
		try {
			this.stockFishWriter.write(StockfishCommands.START_NEW_GAME);
			this.stockFishWriter.flush();
			this.stockFishWriter.write(StockfishCommands.SET_FEN_POSITION + this.currentFen + "\n");
			this.stockFishWriter.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Sender kommando: " + StockfishCommands.SET_FEN_POSITION + this.currentFen + "\n");
		System.out.println("Sender kommando: " + StockfishCommands.NEXT_MOVE + "\n");
		return this.sendCommand(
				StockfishCommands.NEXT_MOVE + "\n",
				StockfishReturns.BESTMOVE);
	}

	private String readResponse(String stopMark)
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
		return temp;
	}

	private String sendCommand(String command, String stopMark)
	{
		try 
		{
			this.stockFishWriter.write(command);
			this.stockFishWriter.flush();
			return this.readResponse(stopMark);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Error: Something went wrong with sendCommand";
	}

	private String getMovesHistory()
    {
        return movesString;
    }
}
