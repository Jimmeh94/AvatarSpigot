package avatar.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Manager<T> {
    // ---------------------------------------------------
    protected List<T> objects = new CopyOnWriteArrayList<>();

    public void add(T object){
        objects.add(object);
    }

    public void remove(T object){
        objects.remove(object);
    }
}
