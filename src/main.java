class main 
{	
	public static void main(String[] args)
	{
		new HovedMeny();
		Player playerA = new Player("Player B", false, 1);
			
		new ChessBoard(playerA);
	}
}
