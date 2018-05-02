package Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class Logger extends Observable {
    private final List<Log> logList = new ArrayList<>();

    public void setChatLog(String playerName, String message) {
        Log logTemp = new Log(LogType.CHAT, playerName, message);
        logList.add(logTemp);
        setChanged();
    }

    public Log setIllegalMove(String playerName, String message)
    {
        Log temp = new Log(LogType.ILLEGALMOVE, playerName, " invalid move: " + message);
        logList.add(temp);
        return temp;
    }

    public Log setMoveLog(String playerName, String message) {
        Log temp = new Log(LogType.MOVE, playerName, message);
        logList.add(temp);
        return temp;
    }

    public Log setStockfishLog(String playerName, String message) {

        Log temp = new Log(LogType.STOCKFISH, playerName, message);
        logList.add(temp);
        return temp;
    }

    public List<Log> getLog() {
        return logList;
    }

    public List<String> getLogOfType(LogType logType) {
        ArrayList logItems = new ArrayList();
        for (Log logItem : logList) {
            if (logItem.getType() == logType) {
                logItems.add(logItem.getMessage());
            }
        }
        return logItems;
    }

}
