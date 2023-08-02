import java.util.HashMap;

public class Player extends Character{

    // fields and attributes
    private int level;
    private int exp;
    private HashMap<String, String> items;

    // constructors
    public Player(String name) {
        super(name);
    }

    public Player(String name, int hp) {
        super(name, hp);
    }

    // methods

}