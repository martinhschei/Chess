class main {
	
	public static void main(String[] args)
	{
		ChessBoard b = new ChessBoard();
		Network network = new Network(1337);
		network.listen();
	}

}
