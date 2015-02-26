package Quiz;

/**
 * Created by Ahmed on 2/26/2015.
 */
public class PlayerImpl {
    private String name;
    private static int IDCOUNT = 0;
    private int id;

    public PlayerImpl(String name) {
        this.name =  name;
        IDCOUNT++;
        this.id = IDCOUNT;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
