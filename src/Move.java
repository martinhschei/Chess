class Move {

    private final Field from;
    private final Field to;
    private final ChessPiece piece;

    public Move(Field from, Field to, ChessPiece piece)
    {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public String toString(){
        return this.from.toString() + this.to.toString();
    }
}
