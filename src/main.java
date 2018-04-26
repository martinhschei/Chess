class main 
{	
	public static void main(String[] args)
	{	
		//Player = new OptionPanel();
		
		Player playerA = new Player("Player", true, 1);
		playerA.setHost();
		
		Player playerB = new Player("Player", true, 1);

		new ChessBoard(playerA);
		new ChessBoard(playerB);
	}
}
