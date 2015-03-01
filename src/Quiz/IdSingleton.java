package Quiz;

import java.io.Serializable;

/**
 * Created by Ahmed on 3/1/2015.
 */
public class IdSingleton implements Serializable {
    private int id;

    public IdSingleton() {
        id = 1;
    }

    public synchronized int generateID() {
        return id++;
    }

}
