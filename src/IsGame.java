interface IsGame {
    boolean myTurn();
    boolean amIWhite();
    boolean isMoveLegal(Move move);
    void onBoardAction(Action action);
}