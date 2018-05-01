import java.util.ArrayList;
import java.util.List;

abstract class HasListeners {

    private List<IsListener> listeners = new ArrayList<>();

    void addListener(IsListener listener)
    {
        this.listeners.add(listener);
    }

    void publishNewChatMessage(Log log)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsActionListener) {
                ((IsLogListener) listener).onNewLogEntry(log);
            }
        }
    }

    void publishAction(Action action)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsActionListener) {
                ((IsActionListener) listener).onNewAction(action);
            }
        }
    }

    void publishNewMove(Move move)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsMoveListener) {
                ((IsMoveListener) listener).onNewMove(move);
            }
        }
    }
}
