import java.io.Serializable;

public class Log implements Serializable {
    private LogType type;
    private String playerName;
    private String message;

    public Log(LogType type, String playerName, String message) {
        this.type = type;
        this.playerName = playerName;
        this.message = message;
    }

    public LogType getType() {
        return type;
    }

    public String getPlayerName() { return playerName; }

    public String getMessage() {
        return message;
    }
}
