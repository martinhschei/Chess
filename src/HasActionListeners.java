import java.util.ArrayList;
import java.util.List;

public abstract class HasActionListeners {

    private List<IsActionListener> listeners = new ArrayList<>();

    public void addActionListener(IsActionListener listener)
    {
        this.listeners.add(listener);
    }

    public void publishAction(Action action)
    {
        for(IsActionListener listener : listeners) {
            listener.newAction(action);
        }
    }

}
