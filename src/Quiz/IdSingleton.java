package Quiz;

import java.io.Serializable;

/**
 * A singleton class used for generating ID numbers, specifically for the class Quiz
 * Created by Ahmed
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
