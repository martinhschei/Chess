class main 
{
	public static void main(String[] args)
	{
		Player playerA = new Player("Player A", true, 1);
		playerA.setHost();

		//Player playerB = new Player("Player B", false, 2);

		new ChessBoard(playerA);
		//new ChessBoard(playerB);

	}
}
