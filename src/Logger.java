import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Logger {
    static private List<Log> logList = new ArrayList<>();

    public static void setLog(Log log) {
        logList.add(log);
    }

    public static List<Log> getLog() {
        return logList;
    }

}
