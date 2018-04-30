interface IsGame {
    boolean myTurn();
    boolean amIWhite();
    void onNewChat(String message);
    boolean isMoveLegal(Move move);
}