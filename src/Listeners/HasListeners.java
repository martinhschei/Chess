package Listeners;

import Helpers.*;
import Interfaces.*;

import java.util.ArrayList;
import java.util.List;

public abstract class HasListeners {

    private final List<IsListener> listeners = new ArrayList<>();

    public void addListener(IsListener listener)
    {
        this.listeners.add(listener);
    }

    protected void publishNewChatMessage(Log log)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsLogListener) {
                ((IsLogListener) listener).onNewLogEntry(log);
            }
        }
    }

    public void publishAction(Action action)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsActionListener) {
                ((IsActionListener) listener).onNewAction(action);
            }
        }
    }

    public void publishNewMove(Move move)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsMoveListener) {
                ((IsMoveListener) listener).onNewMove(move);
            }
        }
    }

}
