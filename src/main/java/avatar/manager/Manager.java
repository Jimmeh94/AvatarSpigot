package avatar.manager;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager<T> {
    // ---------------------------------------------------
    protected List<T> objects = new ArrayList<>();

    public void add(T object){
        objects.add(object);
    }

    public void remove(T object){
        objects.remove(object);
    }
}
