interface IsGame {
    boolean myTurn();
    boolean amIWhite();
    void onNewChat(String message);
}