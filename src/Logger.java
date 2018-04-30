import java.util.ArrayList;
import java.util.List;


public final class Logger {
    static private List<Log> logList = new ArrayList<>();

    public static void setChatLog(String message) {
        logList.add(new Log(LogType.CHAT, message));
    }
    public static void setMoveLog(String message) {
        logList.add(new Log(LogType.MOVE, message));
    }
    public static void setStockfishLog(String message) {
        logList.add(new Log(LogType.STOCKFISH, message));
    }

    public static List<Log> getLog() {
        return logList;
    }

    public static List<String> getLogOfType(LogType logType) {
        ArrayList logItems = new ArrayList();
        for (Log logItem : logList) {
            if (logItem.getType() == logType) {
                logItems.add(logItem.getMessage());
            }
        }
        return logItems;
    }
}
