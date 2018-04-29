import java.util.ArrayList;
import java.util.List;

public abstract class HasListeners {

    private List<IsListener> listeners = new ArrayList<>();

    public void addListener(IsListener listener)
    {
        this.listeners.add(listener);
    }

    public void publishAction(Action action)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsActionListener) {
                ((IsActionListener) listener).newAction(action);
            }
        }
    }

    public void publishNewMove(Move move)
    {
        for(IsListener listener : listeners) {
            if (listener instanceof IsMoveListener) {
                ((IsMoveListener) listener).newMove(move);
            }
        }
    }
}
