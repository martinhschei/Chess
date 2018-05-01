package Interfaces;
import Helpers.*;

public interface IsGame {
    boolean myTurn();
    boolean amIWhite();
    boolean isMoveLegal(Move move);
    void askStockFish();
    Player getPlayer();
    Log logIllegalMove(String playerName, String message);
    String returnPlayerColor(Player player);
}