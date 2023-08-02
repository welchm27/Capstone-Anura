import com.google.gson.Gson;

class Main {
    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        gameLogic.execute();

        String json = "{\"name\":\"purple frog\"}";

        Gson gson = new Gson();

        Character cha = gson.fromJson(json, Character.class);

        System.out.println(cha.getName());

        cha.setName("Yellow Frog");
        System.out.println(cha.getName());
    }
}