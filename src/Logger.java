import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class Logger extends Observable {
    private List<Log> logList = new ArrayList<>();

    public Log setChatLog(String message) {
        Log logTemp = new Log(LogType.CHAT, message);
        logList.add(logTemp);
        setChanged();
        return logTemp;
    }
    public void setMoveLog(String message) {

        logList.add(new Log(LogType.MOVE, message));
        //System.out.println("TEST" + message);
    }
    public void setStockfishLog(String message) {
        logList.add(new Log(LogType.STOCKFISH, message));
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
