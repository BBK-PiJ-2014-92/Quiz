package Quiz;

import Interfaces.Player;

/**
 * Created by Ahmed on 2/26/2015.
 */
public class PlayerImpl implements Player {
    private String name;
    private static int IDCOUNT = 0;
    private int id;

    public PlayerImpl(String name) {
        this.name =  name;
        IDCOUNT++;
        this.id = IDCOUNT;
    }


    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
