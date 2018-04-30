public class Log {
    private LogType type;
    private String message;

    public Log(LogType type, String message) {
        this.type = type;
        this.message = message;
    }

    public LogType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
