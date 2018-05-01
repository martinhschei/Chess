interface IsGame {
    boolean myTurn();
    boolean amIWhite();
    boolean isMoveLegal(Move move);
    Player getPlayer();
    void addToLog(LogType logType, String playerName, String message);
}