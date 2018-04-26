class main {
	
	public static void main(String[] args)
	{
		Network network = new Network(1337);
		network.listen();
		ChessBoard b = new ChessBoard();
	}

}
