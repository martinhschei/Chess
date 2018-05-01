package Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class Logger extends Observable {
    private final List<Log> logList = new ArrayList<>();

    public Log setChatLog(String playerName, String message) {
        Log logTemp = new Log(LogType.CHAT, playerName, message);
        logList.add(logTemp);
        setChanged();
        return logTemp;
    }

    public Log setIllegalMove(String playerName, String message)
    {
        Log temp = new Log(LogType.ILLEGALMOVE, playerName, " ugyldig trekk: " + message);
        logList.add(temp);
        return temp;
    }

    public Log setMoveLog(String playerName, String message) {
        Log temp = new Log(LogType.MOVE, playerName, message);
        logList.add(temp);
        return temp;
    }

    public void setStockfishLog(String playerName, String message) {

        logList.add(new Log(LogType.STOCKFISH, playerName, message));
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
