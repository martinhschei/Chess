class main 
{
	public static void main(String[] args)
	{
		PlayerObservable player1 = new PlayerObservable();
		PlayerObserver observer = new PlayerObserver();
		player1.addObserver(observer);
		new HovedMeny(player1);
		Player playerA = new Player("Player B", false, 1);
			
		new ChessBoard(playerA);
	}
}
