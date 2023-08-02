public class Character {
    // fields and attributes
    private String name;
    private int hp;

    public Character(){}

    public Character(String name) {
        this.name = name;
    }

    public Character(String name, int hp){
        super();
        this.hp = hp;
    }

    // methods
    public void talk(){

    }

    // TODO: subject to change according to interaction with map
    public void move(){

    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public String toString(){
        return this.name;
    }
}