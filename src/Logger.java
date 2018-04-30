import java.util.ArrayList;
import java.util.List;

public final class Logger {
    static private List<Log> logList = new ArrayList<>();
    public static void setLog(Log log) {
        logList.add(log);
    }
    public static List<Log> getLog() {
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
