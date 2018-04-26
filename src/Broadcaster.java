import java.io.ObjectOutputStream;

public abstract class Broadcaster {

    protected ObjectOutputStream broadcaster;

    public void broadcast(Action action) {
        try {
            this.broadcaster.writeObject(action);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
