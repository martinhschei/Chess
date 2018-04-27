interface IsMover {
    void move(ChessPiece piece, Field from, Field to, boolean otherPlayer);
    boolean clickAllowed(Field field);
}
